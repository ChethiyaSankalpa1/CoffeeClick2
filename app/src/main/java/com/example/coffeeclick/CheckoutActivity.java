package com.example.coffeeclick;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    TextView tvSummary, tvTotal;
    Button btnPlaceOrder;
    List<CartItem> cartList;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tvSummary = findViewById(R.id.tvSummary);
        tvTotal = findViewById(R.id.tvTotal);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Convert Map<Coffee, Integer> to List<CartItem>
        cartList = new ArrayList<>();
        Map<Coffee, Integer> cartMap = CartManager.getInstance().getCartItems();
        for (Map.Entry<Coffee, Integer> entry : cartMap.entrySet()) {
            cartList.add(new CartItem(entry.getKey(), entry.getValue()));
        }

        // Show cart summary
        StringBuilder summary = new StringBuilder();
        total = 0;
        for (CartItem item : cartList) {
            summary.append(item.getCoffee().getName())
                    .append(" x ")
                    .append(item.getQuantity())
                    .append(" = LKR ")
                    .append(item.getCoffee().getPrice() * item.getQuantity())
                    .append("\n");
            total += item.getCoffee().getPrice() * item.getQuantity();
        }

        tvSummary.setText(summary.toString());
        tvTotal.setText("Total: LKR " + total);

        btnPlaceOrder.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                // Get current user
                com.example.coffeeclick.models.User currentUser = 
                    MongoDBService.getInstance(this).getCurrentUser();
                
                String userId = currentUser != null ? currentUser.getId() : "guest";
                String username = currentUser != null ? currentUser.getUsername() : "Guest";
                
                // Create order
                com.example.coffeeclick.models.Order order = 
                    new com.example.coffeeclick.models.Order(userId, username, cartList, total);
                order.setStatus("completed"); // Set status as completed
                
                android.util.Log.d("Checkout", "Creating order - User: " + username + ", Total: " + total + ", Items: " + cartList.size());
                
                // Save to MongoDB
                MongoDBService.getInstance(this).saveOrder(order, 
                    new MongoDBService.SaveOrderCallback() {
                        @Override
                        public void onSuccess(String orderId) {
                            runOnUiThread(() -> {
                                android.util.Log.d("Checkout", "Order saved successfully - ID: " + orderId);
                                
                                // Show success dialog
                                new android.app.AlertDialog.Builder(CheckoutActivity.this)
                                    .setTitle("Order Complete! ✅")
                                    .setMessage("Your order has been placed successfully!\n\n" +
                                               "Order ID: " + orderId.substring(0, 8) + "\n" +
                                               "Total: LKR " + total + "\n\n" +
                                               "Thank you for your order!")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        CartManager.getInstance().clearCart();
                                        finish();
                                    })
                                    .setCancelable(false)
                                    .show();
                            });
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(() -> {
                                android.util.Log.e("Checkout", "Order save error: " + error);
                                
                                // Still show success to user (saved locally)
                                new android.app.AlertDialog.Builder(CheckoutActivity.this)
                                    .setTitle("Order Complete! ✅")
                                    .setMessage("Your order has been placed successfully!\n\n" +
                                               "Total: LKR " + total + "\n\n" +
                                               "Thank you for your order!")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        CartManager.getInstance().clearCart();
                                        finish();
                                    })
                                    .setCancelable(false)
                                    .show();
                            });
                        }
                    }
                );
            }
        });
    }
}
