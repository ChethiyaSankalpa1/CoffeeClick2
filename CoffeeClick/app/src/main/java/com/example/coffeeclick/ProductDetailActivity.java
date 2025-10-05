package com.example.coffeeclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView tvName = findViewById(R.id.tvProductName);
        TextView tvPrice = findViewById(R.id.tvProductPrice);
        ImageView ivImage = findViewById(R.id.ivProductImage);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int price = intent.getIntExtra("price",0);
        int image = intent.getIntExtra("image",0);

        tvName.setText(name);
        tvPrice.setText("LKR " + price);
        ivImage.setImageResource(image);

        btnAddToCart.setOnClickListener(v -> {
            Toast.makeText(this, name + " added to cart!", Toast.LENGTH_SHORT).show();
        });
    }
}
