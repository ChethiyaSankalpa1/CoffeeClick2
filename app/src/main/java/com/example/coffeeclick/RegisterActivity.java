package com.example.coffeeclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btnRegister, btnToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnToLogin = findViewById(R.id.btnToLogin);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirm = etConfirmPassword.getText().toString().trim();

            if(username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
                return;
            }

            if(!password.equals(confirm)){
                Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                return;
            }

            // Register user with MongoDB
            MongoDBService.getInstance(this).registerUser(username, email, password, 
                new MongoDBService.RegisterCallback() {
                    @Override
                    public void onSuccess(com.example.coffeeclick.models.User user) {
                        runOnUiThread(() -> {
                            Toast.makeText(RegisterActivity.this, 
                                "Registered successfully! Waiting for admin approval.", 
                                Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> 
                            Toast.makeText(RegisterActivity.this, 
                                "Registration failed: " + error, Toast.LENGTH_SHORT).show()
                        );
                    }
                }
            );
        });

        btnToLogin.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }
}
