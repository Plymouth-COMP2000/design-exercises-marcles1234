package com.example.comp2000cw1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DishView extends RecyclerView.Adapter<DishView.DishViewHolder> {

    private List<String> dishes;

    public DishView(List<String> dishes) {
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dishes, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        String dishName = dishes.get(position);
        holder.dishButton.setText(dishName);
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    static class DishViewHolder extends RecyclerView.ViewHolder {
        Button dishButton;

        DishViewHolder(View itemView) {
            super(itemView);
            dishButton = itemView.findViewById(R.id.dishButton);
        }
    }
}

