package com.example.meal;

import android.content.Intent;
import android.os.Bundle;
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

        requestQueue = Volley.newRequestQueue(this);

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

        searchView = findViewById(R.id.searchView);

        // Обработка событий поиска
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (searchAMeal(query)) {
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAMeal(newText);
                return false;
            }
        });


    }

    private boolean searchAMeal(String text) {

        // Использование segmented button для выбора между поиском по названию блюда или по ингредиенту
        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggleButton);

        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.buttonSearchByMeal) {
                searchByMeal(text);
            } else if (checkedId == R.id.buttonSearchByIngredient) {
                searchByIngredient(text);
            }
        });


        return false;
    }

    private void searchByIngredient(String ingredient) {
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + ingredient;
    }

    private void searchByMeal(String name) {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + name;

    }

}