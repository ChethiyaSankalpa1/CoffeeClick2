package com.example.coffeeclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Login with MongoDB
            MongoDBService.getInstance(this).loginUser(username, password, 
                new MongoDBService.LoginCallback() {
                    @Override
                    public void onSuccess(com.example.coffeeclick.models.User user) {
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, 
                                "Login successful!", Toast.LENGTH_SHORT).show();
                            
                            // Check if user is admin
                            if (user.isAdmin()) {
                                // Admin user - go to Admin Dashboard
                                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                                startActivity(intent);
                            } else {
                                // Regular user - go to Home (Modern UI)
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("username", user.getUsername());
                                startActivity(intent);
                            }
                            finish();
                        });
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> 
                            Toast.makeText(LoginActivity.this, 
                                "Login failed: " + error, Toast.LENGTH_SHORT).show()
                        );
                    }
                }
            );
        });

        btnToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
