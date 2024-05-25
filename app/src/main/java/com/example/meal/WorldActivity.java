package com.example.meal;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class WorldActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    ArrayList<Country> countries = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_world);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/us.png","American"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/gb.png", "British"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/ca.png","Canadian"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/cn.png","Chinese"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/hr.png","Croatian"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/nl.png","Dutch"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/eg.png","Egyptian"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/ph.png","Filipino"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/fr.png","French"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/gr.png","Greek"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/in.png","Indian"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/ie.png","Irish"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/it.png","Italian"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/jm.png","Jamaican"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/jp.png","Japanese"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/kn.png","Kenyan"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/my.png","Malaysian"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/mx.png","Mexican"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/ma.png","Moroccan"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/pl.png","Polish"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/pt.png","Portuguese"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/ru.png","Russian"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/es.png","Spanish"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/th.png","Thai"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/tn.png","Tunisian"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/tr.png","Turkish"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/vn.png","Vietnamese"));
        countries.add(new Country("https://www.themealdb.com/images/icons/flags/big/64/no.png","Other"));


        recyclerView = findViewById(R.id.recyclerViewWorld);

        countryAdapter = new CountryAdapter(WorldActivity.this, countries);
        recyclerView.setAdapter(countryAdapter);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.worldButton);
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