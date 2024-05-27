package com.example.meal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meal.adapter.IngredientAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealPageActivity extends AppCompatActivity {
    ImageView dishPageImageView;
    TextView dishPageTitleTextView, dishPageCategoryTextView, dishPageInstructionTextView;
    RecyclerView dishPageIngredientsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Идентифицирование элементов на макете
        dishPageImageView = findViewById(R.id.dishPageImageView);
        dishPageTitleTextView = findViewById(R.id.dishPageTitleTextView);
        dishPageCategoryTextView = findViewById(R.id.dishPageCategoryTextView);
        dishPageInstructionTextView = findViewById(R.id.dishPageInstructionTextView);
        dishPageIngredientsRecyclerView = findViewById(R.id.dishPageIngredientsRecyclerView);

        // Заполнение элементов на макете
        Picasso.get().load(getIntent().getStringExtra("picture")).fit().centerCrop().into(dishPageImageView);
        dishPageImageView.setImageResource(getIntent().getIntExtra("picture", 0));
        dishPageTitleTextView.setText(getIntent().getStringExtra("name"));
        dishPageCategoryTextView.setText(getIntent().getStringExtra("category"));
        dishPageInstructionTextView.setText(getIntent().getStringExtra("instructions"));

        // Заполнение ингредиентов через IngredientAdapter
        ArrayList<String> ingredients = getIntent().getStringArrayListExtra("ingredients");
        if (ingredients != null) {
            IngredientAdapter ingredientAdapter = new IngredientAdapter(this, ingredients);
            dishPageIngredientsRecyclerView.setAdapter(ingredientAdapter);
            dishPageIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        // Работа с нижним меню
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // bottomNavigationView.setSelectedItemId(R.id.worldButton);
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
}