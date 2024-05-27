package com.example.meal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context context;
    private ArrayList<String> ingredients;

    public IngredientAdapter(Context context, ArrayList<String> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        String ingredient = ingredients.get(position);
        holder.ingredientTextView.setText(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientTextView;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.ingredientTextView);
        }
    }
}
