package com.example.coffeeclick;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerViewCategory, recyclerViewPopular;
    EditText editTextSearch;
    TextView tvCartBadge;
    LinearLayout navExplore, navCart, navChatbot, navOrders, navProfile;
    List<Coffee> coffeeList;
    List<Coffee> allCoffeeList;
    List<String> categoryList;
    CoffeeAdapter adapter;
    CategoryAdapter categoryAdapter;
    String selectedCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);

        // Initialize database with context
        CoffeeDatabase.init(this);

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

        // Load coffee from database
        allCoffeeList = CoffeeDatabase.getAllCoffees();
        coffeeList = new ArrayList<>(allCoffeeList);
        categoryList = CoffeeDatabase.getCategories();
        
        android.util.Log.d("HomeActivity", "Total coffee items loaded: " + allCoffeeList.size());
        android.util.Log.d("HomeActivity", "Categories loaded: " + categoryList.size());
        
        // Log all coffee items with categories
        for (Coffee coffee : allCoffeeList) {
            android.util.Log.d("HomeActivity", "Coffee: " + coffee.getName() + " | Category: " + coffee.getCategory() + " | Popular: " + coffee.isPopular());
        }
        
        // Setup popular coffees (grid layout - 2 columns)
        adapter = new CoffeeAdapter(this, coffeeList);
        recyclerViewPopular.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewPopular.setAdapter(adapter);
        
        // Setup category (horizontal with proper adapter)
        if (recyclerViewCategory != null) {
            categoryAdapter = new CategoryAdapter(categoryList, (category, position) -> {
                android.util.Log.d("HomeActivity", "===== CATEGORY CLICKED =====");
                android.util.Log.d("HomeActivity", "Category: " + category + " | Position: " + position);
                selectedCategory = category;
                filterByCategory(category);
            });
            recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewCategory.setAdapter(categoryAdapter);
            android.util.Log.d("HomeActivity", "Category RecyclerView setup complete with " + categoryList.size() + " categories");
        } else {
            android.util.Log.e("HomeActivity", "recyclerViewCategory is NULL!");
        }
        
        android.util.Log.d("HomeActivity", "Setup complete - Showing " + coffeeList.size() + " coffee items");

        // Search functionality
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCoffee(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
        
        navExplore.setOnClickListener(v -> {
            android.util.Log.d("HomeActivity", "===== EXPLORE CLICKED =====");
            
            // Reset category to "All"
            selectedCategory = "All";
            if (categoryAdapter != null) {
                categoryAdapter.setSelectedPosition(0); // "All" is at position 0
            }
            
            // Clear search
            if (editTextSearch != null) {
                editTextSearch.setText("");
            }
            
            // Show all items
            coffeeList.clear();
            coffeeList.addAll(allCoffeeList);
            
            android.util.Log.d("HomeActivity", "Showing all " + coffeeList.size() + " items");
            
            // Notify adapter
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            
            // Scroll to top
            if (recyclerViewPopular != null) {
                recyclerViewPopular.smoothScrollToPosition(0);
            }
            
            // Show toast
            android.widget.Toast.makeText(this, 
                "Showing all " + coffeeList.size() + " items", 
                android.widget.Toast.LENGTH_SHORT).show();
            
            android.util.Log.d("HomeActivity", "===== EXPLORE COMPLETE =====");
        });

        navCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        navChatbot.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatBotActivity.class);
            startActivity(intent);
        });

        navOrders.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MyOrdersActivity.class);
            startActivity(intent);
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
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
        android.util.Log.d("HomeActivity", "Searching for: " + query);
        
        coffeeList.clear();
        
        if (query.isEmpty()) {
            coffeeList.addAll(allCoffeeList);
        } else {
            String searchQuery = query.toLowerCase();
            for (Coffee coffee : allCoffeeList) {
                String coffeeName = coffee.getName().toLowerCase();
                String coffeeDesc = coffee.getDescription().toLowerCase();
                String coffeeCategory = coffee.getCategory().toLowerCase();
                
                // Search in name, description, or category
                if (coffeeName.contains(searchQuery) || 
                    coffeeDesc.contains(searchQuery) || 
                    coffeeCategory.contains(searchQuery)) {
                    coffeeList.add(coffee);
                }
            }
        }
        
        android.util.Log.d("HomeActivity", "Search results: " + coffeeList.size() + " items found");
        
        // Update grid adapter
        adapter.notifyDataSetChanged();
    }
    
    private void filterByCategory(String category) {
        android.util.Log.d("HomeActivity", "========== FILTERING BY CATEGORY ==========");
        android.util.Log.d("HomeActivity", "Category: " + category);
        android.util.Log.d("HomeActivity", "Current list size: " + coffeeList.size());
        
        coffeeList.clear();
        android.util.Log.d("HomeActivity", "List cleared");
        
        if (category.equals("All")) {
            coffeeList.addAll(allCoffeeList);
            android.util.Log.d("HomeActivity", "Added all " + allCoffeeList.size() + " items");
        } else {
            int count = 0;
            for (Coffee coffee : allCoffeeList) {
                if (coffee.getCategory() != null && coffee.getCategory().equals(category)) {
                    coffeeList.add(coffee);
                    count++;
                    android.util.Log.d("HomeActivity", count + ". Added: " + coffee.getName() + " (Category: " + coffee.getCategory() + ")");
                }
            }
            android.util.Log.d("HomeActivity", "Total added: " + count + " items");
        }
        
        android.util.Log.d("HomeActivity", "New list size: " + coffeeList.size());
        android.util.Log.d("HomeActivity", "Calling adapter.notifyDataSetChanged()");
        
        // Force adapter update
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            android.util.Log.d("HomeActivity", "Adapter notified");
        } else {
            android.util.Log.e("HomeActivity", "Adapter is NULL!");
        }
        
        // Force RecyclerView update
        if (recyclerViewPopular != null) {
            recyclerViewPopular.post(() -> {
                recyclerViewPopular.smoothScrollToPosition(0);
                android.util.Log.d("HomeActivity", "RecyclerView scrolled to top");
            });
        } else {
            android.util.Log.e("HomeActivity", "RecyclerView is NULL!");
        }
        
        // Clear search when category changes
        if (editTextSearch != null) {
            editTextSearch.setText("");
        }
        
        // Show toast for user feedback
        android.widget.Toast.makeText(this, 
            "Showing " + coffeeList.size() + " " + category + " items", 
            android.widget.Toast.LENGTH_SHORT).show();
        
        android.util.Log.d("HomeActivity", "========== FILTER COMPLETE ==========");
    }
}
