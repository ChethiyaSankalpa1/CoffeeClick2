package com.example.coffeeclick;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeclick.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView rvOrders;
    android.widget.TextView tvNoOrders;
    android.widget.Button btnBack;
    OrderHistoryAdapter adapter;
    List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        rvOrders = findViewById(R.id.rvOrders);
        tvNoOrders = findViewById(R.id.tvNoOrders);
        btnBack = findViewById(R.id.btnBack);
        orderList = new ArrayList<>();

        btnBack.setOnClickListener(v -> finish());

        adapter = new OrderHistoryAdapter(orderList);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.setAdapter(adapter);

        loadOrders();
    }

    private void loadOrders() {
        MongoDBService.getInstance(this).getAllOrders(new MongoDBService.GetOrdersCallback() {
            @Override
            public void onSuccess(List<Order> orders) {
                runOnUiThread(() -> {
                    orderList.clear();
                    if (orders != null) {
                        orderList.addAll(orders);
                    }
                    
                    // Show/hide empty message
                    if (orderList.isEmpty()) {
                        tvNoOrders.setVisibility(android.view.View.VISIBLE);
                        rvOrders.setVisibility(android.view.View.GONE);
                    } else {
                        tvNoOrders.setVisibility(android.view.View.GONE);
                        rvOrders.setVisibility(android.view.View.VISIBLE);
                    }
                    
                    adapter.notifyDataSetChanged();
                    android.util.Log.d("OrderHistory", "Loaded " + orderList.size() + " orders");
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(OrderHistoryActivity.this, 
                        "Error loading orders: " + error, Toast.LENGTH_SHORT).show();
                    android.util.Log.e("OrderHistory", "Error: " + error);
                });
            }
        });
    }
}
