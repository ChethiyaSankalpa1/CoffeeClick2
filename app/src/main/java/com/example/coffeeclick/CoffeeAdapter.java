package com.example.coffeeclick;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder> {

    private final Context context;
    private final List<Coffee> coffeeList;
    private OnItemLongClickListener longClickListener;
    private boolean isAdminMode = false;

    public interface OnItemLongClickListener {
        void onItemLongClick(Coffee coffee, int position);
    }

    public CoffeeAdapter(Context context, List<Coffee> coffeeList) {
        this.context = context;
        this.coffeeList = coffeeList;
    }
    
    public void setAdminMode(boolean adminMode) {
        this.isAdminMode = adminMode;
        notifyDataSetChanged();
    }
    
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public CoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coffee, parent, false);
        return new CoffeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeViewHolder holder, int position) {
        Coffee coffee = coffeeList.get(position);

        holder.ivCoffeeImage.setImageResource(coffee.getImageResId());
        holder.tvCoffeeName.setText(coffee.getName());
        holder.tvCoffeePrice.setText("LKR " + coffee.getPrice());
        holder.tvQuantity.setText(String.valueOf(coffee.getQuantity()));
        
        // Set rating
        if (holder.tvRating != null) {
            holder.tvRating.setText("⭐ " + coffee.getRating());
        }
        
        // Set category
        if (holder.tvCategory != null) {
            holder.tvCategory.setText(coffee.getCategory());
        }
        
        // Set description
        if (holder.tvDescription != null) {
            holder.tvDescription.setText(coffee.getDescription());
        }

        // Plus button
        holder.btnPlus.setOnClickListener(v -> {
            v.setPressed(true);
            coffee.setQuantity(coffee.getQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(coffee.getQuantity()));
            android.util.Log.d("CoffeeAdapter", "Plus clicked - " + coffee.getName() + " quantity: " + coffee.getQuantity());
        });

        // Minus button
        holder.btnMinus.setOnClickListener(v -> {
            v.setPressed(true);
            if (coffee.getQuantity() > 0) {
                coffee.setQuantity(coffee.getQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(coffee.getQuantity()));
                android.util.Log.d("CoffeeAdapter", "Minus clicked - " + coffee.getName() + " quantity: " + coffee.getQuantity());
            }
        });

        // Add to Cart
        holder.btnOrder.setOnClickListener(v -> {
            v.setPressed(true);
            if (coffee.getQuantity() > 0) {
                CartManager.getInstance().addItem(coffee, coffee.getQuantity());
                Toast.makeText(context, coffee.getName() + " x" + coffee.getQuantity() + " added to cart", Toast.LENGTH_SHORT).show();
                android.util.Log.d("CoffeeAdapter", "Added to cart - " + coffee.getName() + " x" + coffee.getQuantity());
                coffee.setQuantity(0);
                holder.tvQuantity.setText("0");
            } else {
                Toast.makeText(context, "Please select quantity first", Toast.LENGTH_SHORT).show();
                android.util.Log.d("CoffeeAdapter", "Add to cart failed - quantity is 0");
            }
        });

        // Enable item click to open detail page
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CoffeeDetailActivity.class);
            intent.putExtra("coffee", coffee);
            context.startActivity(intent);
            android.util.Log.d("CoffeeAdapter", "Opening detail page for: " + coffee.getName());
        });
        
        // Show/hide delete button based on admin mode
        if (isAdminMode) {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnOrder.setVisibility(View.GONE);
            holder.btnPlus.setVisibility(View.GONE);
            holder.btnMinus.setVisibility(View.GONE);
            holder.tvQuantity.setVisibility(View.GONE);
            
            // Delete button click
            holder.btnDelete.setOnClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(coffee, holder.getAdapterPosition());
                }
            });
        } else {
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnOrder.setVisibility(View.VISIBLE);
            holder.btnPlus.setVisibility(View.VISIBLE);
            holder.btnMinus.setVisibility(View.VISIBLE);
            holder.tvQuantity.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return coffeeList.size();
    }

    public static class CoffeeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCoffeeImage;
        TextView tvCoffeeName, tvCoffeePrice, tvQuantity, tvRating, tvCategory, tvDescription;
        Button btnPlus, btnMinus, btnOrder, btnDelete;

        public CoffeeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCoffeeImage = itemView.findViewById(R.id.ivCoffeeImage);
            tvCoffeeName = itemView.findViewById(R.id.tvCoffeeName);
            tvCoffeePrice = itemView.findViewById(R.id.tvCoffeePrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnOrder = itemView.findViewById(R.id.btnOrder);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
