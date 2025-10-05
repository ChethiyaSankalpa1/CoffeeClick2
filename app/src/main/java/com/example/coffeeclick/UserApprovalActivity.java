package com.example.coffeeclick;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeclick.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserApprovalActivity extends AppCompatActivity {

    RecyclerView rvUsers;
    android.widget.TextView tvNoUsers;
    android.widget.Button btnBack;
    UserApprovalAdapter adapter;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_approval);

        rvUsers = findViewById(R.id.rvUsers);
        tvNoUsers = findViewById(R.id.tvNoUsers);
        btnBack = findViewById(R.id.btnBack);
        userList = new ArrayList<>();

        btnBack.setOnClickListener(v -> finish());

        adapter = new UserApprovalAdapter(userList, new UserApprovalAdapter.OnUserActionListener() {
            @Override
            public void onApprove(User user) {
                MongoDBService.getInstance(UserApprovalActivity.this).approveUser(
                    user.getId(), 
                    true, 
                    new MongoDBService.ApproveUserCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> {
                                Toast.makeText(UserApprovalActivity.this, 
                                    "User approved", Toast.LENGTH_SHORT).show();
                                loadUsers();
                            });
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(() -> 
                                Toast.makeText(UserApprovalActivity.this, 
                                    "Error: " + error, Toast.LENGTH_SHORT).show()
                            );
                        }
                    }
                );
            }

            @Override
            public void onReject(User user) {
                MongoDBService.getInstance(UserApprovalActivity.this).approveUser(
                    user.getId(), 
                    false, 
                    new MongoDBService.ApproveUserCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> {
                                Toast.makeText(UserApprovalActivity.this, 
                                    "User rejected", Toast.LENGTH_SHORT).show();
                                loadUsers();
                            });
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(() -> 
                                Toast.makeText(UserApprovalActivity.this, 
                                    "Error: " + error, Toast.LENGTH_SHORT).show()
                            );
                        }
                    }
                );
            }
        });

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(adapter);

        loadUsers();
    }

    private void loadUsers() {
        MongoDBService.getInstance(this).getAllUsers(new MongoDBService.GetUsersCallback() {
            @Override
            public void onSuccess(List<User> users) {
                runOnUiThread(() -> {
                    userList.clear();
                    // Filter out admin users
                    for (User user : users) {
                        if (!user.isAdmin()) {
                            userList.add(user);
                        }
                    }
                    
                    // Show/hide empty message
                    if (userList.isEmpty()) {
                        tvNoUsers.setVisibility(android.view.View.VISIBLE);
                        rvUsers.setVisibility(android.view.View.GONE);
                    } else {
                        tvNoUsers.setVisibility(android.view.View.GONE);
                        rvUsers.setVisibility(android.view.View.VISIBLE);
                    }
                    
                    adapter.notifyDataSetChanged();
                    android.util.Log.d("UserApproval", "Loaded " + userList.size() + " users");
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(UserApprovalActivity.this, 
                        "Error loading users: " + error, Toast.LENGTH_SHORT).show();
                    android.util.Log.e("UserApproval", "Error: " + error);
                });
            }
        });
    }
}
