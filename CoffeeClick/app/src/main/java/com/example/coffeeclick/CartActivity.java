package com.example.coffeeclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCart;
    CartAdapter cartAdapter;
    ArrayList<CartItem> cartList;
    TextView tvTotal;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCartItems);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        cartList = new ArrayList<>();
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // Example cart items
        cartList.add(new CartItem(new Coffee("Espresso", 200, R.drawable.espresso), 1));
        cartList.add(new CartItem(new Coffee("Latte", 300, R.drawable.latte), 2));

        cartAdapter = new CartAdapter(cartList);
        rvCart.setAdapter(cartAdapter);

        updateTotal();

        btnCheckout.setOnClickListener(v -> {
            if(cartList.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
            }
        });
    }

    private void updateTotal() {
        int total = 0;
        for (CartItem item : cartList) {
            total += item.getCoffee().getPrice() * item.getQuantity();
        }
        tvTotal.setText("Total: LKR " + total);
    }
}
