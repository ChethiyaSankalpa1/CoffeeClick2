package com.example.coffeeclick;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivityNew extends AppCompatActivity {

    RecyclerView recyclerViewCategory, recyclerViewPopular;
    EditText editTextSearch;
    TextView tvCartBadge;
    LinearLayout navExplore, navCart, navChatbot, navOrders, navProfile;
    List<Coffee> coffeeList;
    List<Coffee> allCoffeeList;
    CoffeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);

        // Initialize views
        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerViewCategory = findViewById(R.id.recyclerViewCategory);
        recyclerViewPopular = findViewById(R.id.recyclerViewPopular);
        tvCartBadge = findViewById(R.id.tvCartBadge);
        
        // Bottom navigation
        navExplore = findViewById(R.id.navExplore);
        navCart = findViewById(R.id.navCart);
        navChatbot = findViewById(R.id.navChatbot);
        navOrders = findViewById(R.id.navOrders);
        navProfile = findViewById(R.id.navProfile);

        // Coffee list
        allCoffeeList = new ArrayList<>();
        allCoffeeList.add(new Coffee("Espresso", 350, R.drawable.espresso, "Strong coffee shot"));
        allCoffeeList.add(new Coffee("Cappuccino", 450, R.drawable.cappuccino, "With milk foam"));
        allCoffeeList.add(new Coffee("Latte", 400, R.drawable.latte, "Smooth milk coffee"));
        
        coffeeList = new ArrayList<>(allCoffeeList);

        // Setup popular coffees (grid layout)
        adapter = new CoffeeAdapter(this, coffeeList);
        recyclerViewPopular.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewPopular.setAdapter(adapter);
        
        // Setup category (horizontal)
        CoffeeAdapter categoryAdapter = new CoffeeAdapter(this, coffeeList);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setAdapter(categoryAdapter);

        // Search functionality
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCoffee(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Bottom navigation
        navExplore.setOnClickListener(v -> {
            // Already on explore (home)
        });

        navCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivityNew.this, CartActivity.class);
            startActivity(intent);
        });

        navChatbot.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivityNew.this, ChatBotActivity.class);
            startActivity(intent);
        });

        navOrders.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivityNew.this, MyOrdersActivity.class);
            startActivity(intent);
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivityNew.this, ProfileActivity.class);
            startActivity(intent);
        });
        
        updateCartBadge();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }
    
    private void updateCartBadge() {
        int cartSize = CartManager.getInstance().getCartSize();
        if (cartSize > 0) {
            tvCartBadge.setVisibility(View.VISIBLE);
            tvCartBadge.setText(String.valueOf(cartSize));
        } else {
            tvCartBadge.setVisibility(View.GONE);
        }
    }
    
    private void filterCoffee(String query) {
        if (query.isEmpty()) {
            coffeeList.clear();
            coffeeList.addAll(allCoffeeList);
        } else {
            coffeeList.clear();
            for (Coffee coffee : allCoffeeList) {
                if (coffee.getName().toLowerCase().contains(query.toLowerCase())) {
                    coffeeList.add(coffee);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
