package com.example.coffeeclick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartList;
    private OnCartUpdateListener listener;

    public CartAdapter(List<CartItem> cartList, OnCartUpdateListener listener) {
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartList.get(position);

        holder.ivCoffee.setImageResource(item.getCoffee().getImageResId());
        holder.tvName.setText(item.getCoffee().getName());
        holder.tvPrice.setText("LKR " + item.getCoffee().getPrice() * item.getQuantity());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        // Increase quantity
        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
            holder.tvPrice.setText("LKR " + (item.getCoffee().getPrice() * item.getQuantity()));
            if (listener != null) {
                listener.onCartUpdated(cartList);
            }
            android.util.Log.d("CartAdapter", "Plus clicked - Quantity: " + item.getQuantity());
        });

        // Decrease quantity
        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
                holder.tvPrice.setText("LKR " + (item.getCoffee().getPrice() * item.getQuantity()));
                if (listener != null) {
                    listener.onCartUpdated(cartList);
                }
                android.util.Log.d("CartAdapter", "Minus clicked - Quantity: " + item.getQuantity());
            } else {
                android.util.Log.d("CartAdapter", "Cannot go below 1");
            }
        });

        // Remove item
        holder.tvRemove.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && currentPosition < cartList.size()) {
                cartList.remove(currentPosition);
                notifyItemRemoved(currentPosition);
                notifyItemRangeChanged(currentPosition, cartList.size());
                if (listener != null) listener.onCartUpdated(cartList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCoffee;
        TextView tvName, tvPrice, tvQuantity, tvRemove;
        Button btnPlus, btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCoffee = itemView.findViewById(R.id.ivCartCoffee);
            tvName = itemView.findViewById(R.id.tvCartCoffeeName);
            tvPrice = itemView.findViewById(R.id.tvCartCoffeePrice);
            tvQuantity = itemView.findViewById(R.id.tvCartQuantity);
            tvRemove = itemView.findViewById(R.id.tvRemove);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }

    public interface OnCartUpdateListener {
        void onCartUpdated(List<CartItem> cartList);
    }
}
