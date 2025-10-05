package com.example.coffeeclick;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Coffee> coffeeList;
    private RecyclerView rvCoffeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvCoffeeList = findViewById(R.id.rvCoffeeList);
//        FloatingActionButton fabCart = findViewById(R.id.fabCart);

        coffeeList = new ArrayList<>();
        coffeeList.add(new Coffee("Espresso", 200, R.drawable.espresso));
        coffeeList.add(new Coffee("Cappuccino", 250, R.drawable.cappuccino));
        coffeeList.add(new Coffee("Latte", 300, R.drawable.latte));
        coffeeList.add(new Coffee("Mocha", 350, R.drawable.mocha));

        CoffeeAdapter adapter = new CoffeeAdapter(this, coffeeList);
        rvCoffeeList.setLayoutManager(new LinearLayoutManager(this));
        rvCoffeeList.setAdapter(adapter);

//        fabCart.setOnClickListener(v -> {
//            Intent intent = new Intent(HomeActivity.this, CheckoutActivity.class);
//            startActivity(intent);
//        });
    }
}
