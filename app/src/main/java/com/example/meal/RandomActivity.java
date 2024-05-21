package com.example.meal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RandomActivity extends AppCompatActivity {
    Dish dish = new Dish();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_random);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestQueue = Volley.newRequestQueue(this);
        getRandomDish();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.randomButton);
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

    private void getRandomDish() {
        String url = "https://www.themealdb.com/api/json/v1/1/random.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("meals");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);


                    String id, name, category, area, instructions, picture, tag;
                    ArrayList<String> ingredients = new ArrayList<>();

                    id = jsonObject1.getString("idMeal");
                    name = jsonObject1.getString("strMeal");
                    category = jsonObject1.getString("strCategory");
                    area = jsonObject1.getString("strArea");
                    instructions = jsonObject1.getString("strInstructions");
                    picture = jsonObject1.getString("strMealThumb");
                    tag = jsonObject1.getString("strTags");


                    String ingredient;
                    for (int j = 1; j <= 15; j++) {
                        ingredient = jsonObject1.getString("strIngredient" + j) + " " + jsonObject1.getString("strMeasure" + j);
                        if (ingredient != null) {
                            ingredients.add(ingredient);
                        } else break;
                    }

                    dish.setId(id);
                    dish.setName(name);
                    dish.setCategory(category);
                    dish.setArea(area);
                    dish.setInstructions(instructions);
                    dish.setTag(tag);
                    dish.setPicture(picture);
                    dish.setIngredients(ingredients);

                    ImageView dishRandomImageView;
                    TextView dishRandomTitleTextView, dishRandomCategoryTextView, dishRandomInstructionTextView;
                    ListView dishPageIngredientsListView;

                    dishRandomImageView = findViewById(R.id.dishRandomImageView);
                    dishRandomTitleTextView = findViewById(R.id.dishRandomTitleTextView);
                    dishRandomCategoryTextView = findViewById(R.id.dishRandomCategoryTextView);
                    dishRandomInstructionTextView = findViewById(R.id.dishRandomInstructionTextView);
                    dishPageIngredientsListView = findViewById(R.id.dishRandomIngredientsTextView);

                    Picasso.get().load(dish.getPicture()).fit().centerCrop().into(dishRandomImageView);
                    dishRandomTitleTextView.setText(dish.getName());
                    dishRandomCategoryTextView.setText(dish.getCategory());
                    dishRandomInstructionTextView.setText(dish.getInstructions());

                    if (ingredients != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(RandomActivity.this, android.R.layout.simple_list_item_1, ingredients);
                        dishPageIngredientsListView.setAdapter(adapter);
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
        requestQueue.add(request);
    }

    public void nextButton(View view) {
        Intent intent = new Intent(this, RandomActivity.class);
        startActivity(intent);
    }

}