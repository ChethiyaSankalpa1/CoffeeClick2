package com.example.coffeeclick;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    private Button btnConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);

        btnConfirmOrder.setOnClickListener(v ->
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
        );
    }
}
