package com.example.meal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    Context context;
    private ArrayList<Country> countries;

    public CountryAdapter(Context context, ArrayList<Country> countries) {
        this.context = context;
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country currentCountry = countries.get(position);

        String flagUrl = currentCountry.getFlagUrl();
        String countryName = currentCountry.getCountryName();

        holder.countryName.setText(countryName);
        Picasso.get().load(flagUrl).fit().centerCrop().into(holder.flag);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DishListActivity.class);

                if ("other".equals(countryName)) {
                    intent.putExtra("url", "https://www.themealdb.com/api/json/v1/1/filter.php?a=Unknown");
                } else {
                    intent.putExtra("url", "https://www.themealdb.com/api/json/v1/1/filter.php?a=" + countryName);
                }

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        ImageView flag;
        TextView countryName;
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            flag = itemView.findViewById(R.id.flag);
            countryName = itemView.findViewById(R.id.countryName);
        }
    }
}
