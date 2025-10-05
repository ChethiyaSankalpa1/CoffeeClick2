package com.example.coffeeclick;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder> {

    private final ArrayList<Coffee> coffeeList;
    private final Context context;

    public CoffeeAdapter(Context context, ArrayList<Coffee> coffeeList) {
        this.context = context;
        this.coffeeList = coffeeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coffee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coffee coffee = coffeeList.get(position);
        holder.tvName.setText(coffee.getName());
        holder.tvPrice.setText("LKR " + coffee.getPrice());
        holder.ivImage.setImageResource(coffee.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("name", coffee.getName());
            intent.putExtra("price", coffee.getPrice());
            intent.putExtra("image", coffee.getImageResId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return coffeeList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivCoffeeImage);
            tvName = itemView.findViewById(R.id.tvCoffeeName);
            tvPrice = itemView.findViewById(R.id.tvCoffeePrice);
        }
    }
}
