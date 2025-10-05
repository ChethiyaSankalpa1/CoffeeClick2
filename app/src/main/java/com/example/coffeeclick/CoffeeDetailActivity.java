package com.example.coffeeclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CoffeeDetailActivity extends AppCompatActivity {

    private TextView tvCartCount, tvQuantity;
    private Coffee coffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);

        ImageView ivCoffeeImage = findViewById(R.id.ivCoffeeImage);
        TextView tvCoffeeName = findViewById(R.id.tvCoffeeName);
        TextView tvCoffeePrice = findViewById(R.id.tvCoffeePrice);
        TextView tvCoffeeDescription = findViewById(R.id.tvCoffeeDescription);
        tvCartCount = findViewById(R.id.tvCartCount);
        tvQuantity = findViewById(R.id.tvQuantity);
        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        Button btnBack = findViewById(R.id.btnBack);
        FrameLayout cartContainer = findViewById(R.id.cartContainer);

        coffee = (Coffee) getIntent().getSerializableExtra("coffee");
        if (coffee != null) {
            ivCoffeeImage.setImageResource(coffee.getImageResId());
            tvCoffeeName.setText(coffee.getName());
            tvCoffeePrice.setText("LKR " + coffee.getPrice());
            tvCoffeeDescription.setText(coffee.getDescription());
        }

        btnPlus.setOnClickListener(v -> {
            coffee.setQuantity(coffee.getQuantity() + 1);
            tvQuantity.setText(String.valueOf(coffee.getQuantity()));
        });

        btnMinus.setOnClickListener(v -> {
            if (coffee.getQuantity() > 0) {
                coffee.setQuantity(coffee.getQuantity() - 1);
                tvQuantity.setText(String.valueOf(coffee.getQuantity()));
            }
        });

        btnAddToCart.setOnClickListener(v -> {
            if (coffee.getQuantity() > 0) {
                CartManager.getInstance().addItem(coffee, coffee.getQuantity());
                android.widget.Toast.makeText(this, coffee.getName() + " x" + coffee.getQuantity() + " added to cart", android.widget.Toast.LENGTH_SHORT).show();
                coffee.setQuantity(0);
                tvQuantity.setText("0");
                updateCartBadge();
                android.util.Log.d("CoffeeDetail", "Added to cart from detail page");
            } else {
                android.widget.Toast.makeText(this, "Please select quantity first", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());

        cartContainer.setOnClickListener(v ->
                startActivity(new Intent(CoffeeDetailActivity.this, CartActivity.class))
        );

        CartManager.getInstance().setCartChangeListener(count -> updateCartBadge());
        updateCartBadge();
    }

    private void updateCartBadge() {
        int total = CartManager.getInstance().getTotalItems();
        if (total > 0) {
            tvCartCount.setText(String.valueOf(total));
            tvCartCount.setVisibility(TextView.VISIBLE);
        } else {
            tvCartCount.setVisibility(TextView.GONE);
        }
    }
}
