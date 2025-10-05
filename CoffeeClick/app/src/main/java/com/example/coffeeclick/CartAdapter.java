package com.example.coffeeclick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final ArrayList<CartItem> cartList;

    public CartAdapter(ArrayList<CartItem> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartList.get(position);
        holder.tvName.setText(item.getCoffee().getName());
        holder.tvPrice.setText("LKR " + item.getCoffee().getPrice());
        holder.ivImage.setImageResource(item.getCoffee().getImageResId());

        holder.tvRemove.setOnClickListener(v -> {
            cartList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvPrice, tvRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivCartCoffee);
            tvName = itemView.findViewById(R.id.tvCartCoffeeName);
            tvPrice = itemView.findViewById(R.id.tvCartCoffeePrice);
            tvRemove = itemView.findViewById(R.id.tvRemove);
        }
    }
}
