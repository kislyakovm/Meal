package com.example.meal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class FindActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchView = findViewById(R.id.searchView);
        // Listener на панель поиска
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Button searchButton = findViewById(R.id.searchButton);

        // Передача поискового запроса в DishListActivity
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchView.getQuery().toString();
                performSearch(query);
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        // Работа с нижним меню
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.findButton);
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

    private void performSearch(String query) {
        /**
         * Метод осуществляет передачу url в DishListActivity для выполнения поиска
         */
        Intent intent = new Intent(FindActivity.this, DishListActivity.class);
        intent.putExtra("url", "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + query);

        startActivity(intent);
    }

    // Пока данные методы не работают. В дальнейшем планируется разветвление поиска
    // на поиск по ингредиенту и поиск по названию блюда
    private void searchByIngredient(String ingredient) {
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + ingredient;
    }

    private void searchByMeal(String name) {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + name;

    }

}