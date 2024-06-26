package com.example.meal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meal.adapter.DishAdapter;
import com.example.meal.model.Dish;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DishListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private DishAdapter dishAdapter;
    private ArrayList<Dish> dishes = new ArrayList<>();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dish_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dishList), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // "стабилзация" размеров. Без этого на макете расплывались размеры
        recyclerView = findViewById(R.id.recycleViewDishList);
        int height = recyclerView.getHeight();
        recyclerView.getLayoutParams().height = height;
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // получение ссылки из Intent
        requestQueue = Volley.newRequestQueue(this);
        String url = getIntent().getStringExtra("url");
        System.out.println(url);
        getDishesByLink(url);

        // Работа с нижним меню
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.listButton) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (itemId == R.id.randomButton) {
                startActivity(new Intent(this, RandomActivity.class));
            } else if (itemId == R.id.findButton) {
                startActivity(new Intent(this, FindActivity.class));
            } else if (itemId == R.id.worldButton) {
                startActivity(new Intent(this, WorldActivity.class));
            }
            return true;
        });

    }

    private void getMealByID(String id) {
        /**
         * Получение рецепта по id (через API). Создание нового объекта (рецепта) и заполнение полей в нем.
         */
        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id;

        RequestQueue requestQueueMeal;
        requestQueueMeal = Volley.newRequestQueue(this);

        // Получение данных по ссылке через API
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("meals");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);


                    String id, name, category, area, instructions, picture, tag;
                    ArrayList<String> ingredients = new ArrayList<>();

                    // Получение данных с сайта
                    id = jsonObject1.getString("idMeal");
                    name = jsonObject1.getString("strMeal");
                    category = jsonObject1.getString("strCategory");
                    area = jsonObject1.getString("strArea");
                    instructions = jsonObject1.getString("strInstructions");
                    picture = jsonObject1.getString("strMealThumb");
                    tag = jsonObject1.getString("strTags");

                    // Получение ингредиентов  с сайта
                    String ingredient, measure, ingredientName;
                    for (int j = 1; j <= 15; j++) {
                        measure = jsonObject1.optString("strMeasure" + j);
                        ingredientName = jsonObject1.optString("strIngredient" + j);

                        if (!TextUtils.isEmpty(measure) && !TextUtils.isEmpty(ingredientName)) {
                            ingredient = ingredientName + " " + measure;
                            ingredients.add(ingredient);
                        } else break;
                    }

                    // Создание и заполнение полей нового блюда
                    Dish dish = new Dish();
                    dish.setId(id);
                    dish.setName(name);
                    dish.setCategory(category);
                    dish.setArea(area);
                    dish.setInstructions(instructions);
                    dish.setTag(tag);
                    dish.setPicture(picture);
                    dish.setIngredients(ingredients);

                    dishes.add(dish);

                    // Решение проблемы ассинхронности в dishes
                    if (dishes.size() == jsonArray.length()) {
                        dishAdapter = new DishAdapter(DishListActivity.this, dishes);
                        recyclerView.setAdapter(dishAdapter);
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        requestQueueMeal.add(request);
    }

    private void getDishesByLink(String url) {
        /**
         * Метод для получения блюд по ссылке через API. Пока используется для получения блюд по
         * названию / стране. В дальнейшем будет расширение по поулчению блюд по категориям
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("meals");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("idMeal");
                        getMealByID(id);
                    }
                    dishAdapter = new DishAdapter(DishListActivity.this, dishes);
                    recyclerView.setAdapter(dishAdapter);
                } catch (JSONException e) {
                    // Если такой ссылки нет - переход на страницу с поиском и вывод сообщения об этом.
                    Intent intent = new Intent(DishListActivity.this, FindActivity.class);
                    startActivity(intent);

                    // Отображение Toast с сообщением "There is no such ingredient"
                    Toast.makeText(DishListActivity.this, "There is no such ingredient", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}