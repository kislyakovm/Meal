package com.example.meal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meal.model.Dish;
import com.example.meal.MealPageActivity;
import com.example.meal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {
    Context context;
    private ArrayList<Dish> dishes;

    public DishAdapter(Context context, ArrayList<Dish> dishes) {
        this.context = context;
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dish_item, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        // Получение информации по элементам для заполнения карточек
        Dish currentDish = dishes.get(position);

        String image = currentDish.getPicture();
        String name = currentDish.getName();
        String category = currentDish.getCategory();
        String area = currentDish.getArea();
        String instructions = currentDish.getInstructions();
        String tag = currentDish.getTag();
        ArrayList<String> ingredients = currentDish.getIngredients();

        // Заполнение элементов на макете
        holder.nameTextView.setText(name);
        holder.categoryTextView.setText(category);
        Picasso.get().load(image).fit().centerCrop().into(holder.imageView);

        // Передача данных в MealPageActivity через Intent
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MealPageActivity.class);

                intent.putExtra("picture", dishes.get(position).getPicture());
                intent.putExtra("name", dishes.get(position).getName());
                intent.putExtra("category", dishes.get(position).getCategory());
                intent.putExtra("area", dishes.get(position).getArea());
                intent.putExtra("instructions", dishes.get(position).getInstructions());
                intent.putExtra("tag", dishes.get(position).getTag());
                intent.putStringArrayListExtra("ingredients", dishes.get(position).getIngredients());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class DishViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView, categoryTextView;
        public DishViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
        }
    }

}
