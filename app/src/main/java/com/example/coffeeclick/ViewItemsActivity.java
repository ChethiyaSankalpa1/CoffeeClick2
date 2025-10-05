package com.example.coffeeclick;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ViewItemsActivity extends AppCompatActivity {

    RecyclerView rvItems;
    Button btnBack;
    TextView tvTitle;
    List<Coffee> coffeeList;
    CoffeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        // Initialize database
        CoffeeDatabase.init(this);

        rvItems = findViewById(R.id.rvItems);
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);

        btnBack.setOnClickListener(v -> finish());

        // Load all items
        coffeeList = CoffeeDatabase.getAllCoffees();
        
        // Setup adapter with admin mode (shows delete buttons)
        adapter = new CoffeeAdapter(this, coffeeList);
        adapter.setAdminMode(true);
        rvItems.setLayoutManager(new GridLayoutManager(this, 2));
        rvItems.setAdapter(adapter);

        // Set delete listener
        adapter.setOnItemLongClickListener((coffee, position) -> {
            showDeleteConfirmation(coffee, position);
        });

        // Update title
        tvTitle.setText("Current Items (" + coffeeList.size() + ")");
        
        android.util.Log.d("ViewItems", "Loaded " + coffeeList.size() + " items");
    }

    private void showDeleteConfirmation(Coffee coffee, int position) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Delete Item");
        builder.setMessage("Are you sure you want to delete \"" + coffee.getName() + "\"?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Remove from database
            CoffeeDatabase.removeCoffee(coffee);
            
            // Remove from local list
            coffeeList.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, coffeeList.size());
            
            // Update title
            tvTitle.setText("Current Items (" + coffeeList.size() + ")");
            
            android.widget.Toast.makeText(this, coffee.getName() + " deleted", android.widget.Toast.LENGTH_SHORT).show();
            android.util.Log.d("ViewItems", "Deleted: " + coffee.getName());
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
