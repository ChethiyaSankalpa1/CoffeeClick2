package com.example.coffeeclick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeclick.models.Order;
import com.example.coffeeclick.models.User;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {

    RecyclerView rvMyOrders;
    TextView tvNoOrders;
    Button btnBack;
    OrderHistoryAdapter adapter;
    List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        rvMyOrders = findViewById(R.id.rvMyOrders);
        tvNoOrders = findViewById(R.id.tvNoOrders);
        btnBack = findViewById(R.id.btnBack);

        orderList = new ArrayList<>();
        adapter = new OrderHistoryAdapter(orderList);

        rvMyOrders.setLayoutManager(new LinearLayoutManager(this));
        rvMyOrders.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        loadMyOrders();
    }

    private void loadMyOrders() {
        User currentUser = MongoDBService.getInstance(this).getCurrentUser();
        
        if (currentUser == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        MongoDBService.getInstance(this).getAllOrders(new MongoDBService.GetOrdersCallback() {
            @Override
            public void onSuccess(List<Order> orders) {
                runOnUiThread(() -> {
                    orderList.clear();
                    
                    // Filter orders for current user only
                    for (Order order : orders) {
                        if (order.getUserId().equals(currentUser.getId())) {
                            orderList.add(order);
                        }
                    }
                    
                    // Show/hide empty message
                    if (orderList.isEmpty()) {
                        tvNoOrders.setVisibility(View.VISIBLE);
                        rvMyOrders.setVisibility(View.GONE);
                    } else {
                        tvNoOrders.setVisibility(View.GONE);
                        rvMyOrders.setVisibility(View.VISIBLE);
                    }
                    
                    adapter.notifyDataSetChanged();
                    android.util.Log.d("MyOrders", "Loaded " + orderList.size() + " orders for user: " + currentUser.getUsername());
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(MyOrdersActivity.this, 
                        "Error loading orders: " + error, Toast.LENGTH_SHORT).show();
                    android.util.Log.e("MyOrders", "Error: " + error);
                });
            }
        });
    }
}
