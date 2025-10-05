package com.example.coffeeclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    Button btnUserApproval, btnOrderHistory, btnManageItems, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnUserApproval = findViewById(R.id.btnUserApproval);
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnManageItems = findViewById(R.id.btnManageItems);
        btnLogout = findViewById(R.id.btnLogout);

        btnUserApproval.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, UserApprovalActivity.class);
            startActivity(intent);
        });

        btnOrderHistory.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, OrderHistoryActivity.class);
            startActivity(intent);
        });

        btnManageItems.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ManageItemsActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            // Clear all activities and go back to welcome page
            Intent intent = new Intent(AdminDashboardActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            android.util.Log.d("AdminDashboard", "Admin logged out - returning to welcome page");
        });
    }
}
