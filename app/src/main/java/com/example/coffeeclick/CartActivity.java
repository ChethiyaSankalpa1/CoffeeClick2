package com.example.coffeeclick;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCart;
    private TextView tvEmpty, tvTotal;
    private android.widget.Button btnCheckout, btnBack;
    private List<CartItem> cartList;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        try {
            rvCart = findViewById(R.id.rvCartItems);
            tvEmpty = findViewById(R.id.tvEmpty);
            tvTotal = findViewById(R.id.tvTotal);
            btnCheckout = findViewById(R.id.btnCheckout);
            btnBack = findViewById(R.id.btnBack);
            
            btnBack.setOnClickListener(v -> finish());

            // Convert cart map to list
            cartList = new ArrayList<>();
            Map<Coffee, Integer> cartMap = CartManager.getInstance().getCartItems();
            
            if (cartMap != null) {
                for (Map.Entry<Coffee, Integer> entry : cartMap.entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        cartList.add(new CartItem(entry.getKey(), entry.getValue()));
                    }
                }
            }

        // Calculate and display initial total
        int initialTotal = 0;
        for (CartItem item : cartList) {
            initialTotal += item.getCoffee().getPrice() * item.getQuantity();
        }
        tvTotal.setText("Total: LKR " + initialTotal);

        // Show empty message if cart is empty
        if (cartList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("Cart is empty");
            rvCart.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvCart.setVisibility(View.VISIBLE);
        }

        // Set up RecyclerView with CartAdapter
        adapter = new CartAdapter(cartList, updatedList -> {
            // Callback for cart updates - just update total, don't clear list!
            android.util.Log.d("CartActivity", "Cart updated - Items: " + updatedList.size());

            // Update empty view
            if (updatedList.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText("Cart is empty");
                rvCart.setVisibility(View.GONE);
                btnCheckout.setEnabled(false);
            } else {
                tvEmpty.setVisibility(View.GONE);
                rvCart.setVisibility(View.VISIBLE);
                btnCheckout.setEnabled(true);
            }

            // Update total
            int total = 0;
            for (CartItem item : updatedList) {
                total += item.getCoffee().getPrice() * item.getQuantity();
            }
            tvTotal.setText("Total: LKR " + total);
            android.util.Log.d("CartActivity", "New total: LKR " + total);
        });

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(adapter);

        // Checkout button
        btnCheckout.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                android.widget.Toast.makeText(this, "Cart is empty!", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                android.content.Intent intent = new android.content.Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
        
        } catch (Exception e) {
            android.util.Log.e("CartActivity", "Error in onCreate", e);
            android.widget.Toast.makeText(this, "Error loading cart: " + e.getMessage(), 
                android.widget.Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        android.util.Log.d("CartActivity", "onResume - Refreshing cart");
        refreshCart();
    }
    
    private void refreshCart() {
        // Reload cart from CartManager
        cartList.clear();
        Map<Coffee, Integer> cartMap = CartManager.getInstance().getCartItems();
        
        if (cartMap != null) {
            for (Map.Entry<Coffee, Integer> entry : cartMap.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    cartList.add(new CartItem(entry.getKey(), entry.getValue()));
                }
            }
        }
        
        android.util.Log.d("CartActivity", "Cart refreshed - Items: " + cartList.size());
        
        // Update empty view
        if (cartList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("Cart is empty");
            rvCart.setVisibility(View.GONE);
            btnCheckout.setEnabled(false);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvCart.setVisibility(View.VISIBLE);
            btnCheckout.setEnabled(true);
        }
        
        // Update total
        int total = 0;
        for (CartItem item : cartList) {
            total += item.getCoffee().getPrice() * item.getQuantity();
        }
        tvTotal.setText("Total: LKR " + total);
        
        // Notify adapter
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            android.util.Log.d("CartActivity", "Adapter notified - Total: LKR " + total);
        }
    }
}
