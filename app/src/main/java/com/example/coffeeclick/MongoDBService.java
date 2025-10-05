package com.example.coffeeclick;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.coffeeclick.models.Order;
import com.example.coffeeclick.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MongoDBService {
    private static final String TAG = "MongoDBService";
    private static MongoDBService instance;
    private Context context;
    private Gson gson;

    private MongoDBService(Context context) {
        this.context = context.getApplicationContext();
        this.gson = new Gson();
        initializeSampleData();
    }

    // Initialize sample data on first run
    private void initializeSampleData() {
        SharedPreferences prefs = context.getSharedPreferences("UsersDB", Context.MODE_PRIVATE);
        boolean isInitialized = prefs.getBoolean("initialized", false);
        
        if (!isInitialized) {
            try {
                JSONArray usersArray = new JSONArray();
                
                // Add Admin User
                JSONObject adminUser = new JSONObject();
                adminUser.put("id", "admin-001");
                adminUser.put("username", "admin");
                adminUser.put("email", "admin@gmail.com");
                adminUser.put("password", "123456");
                adminUser.put("isApproved", true);
                adminUser.put("isAdmin", true);
                adminUser.put("createdAt", System.currentTimeMillis());
                usersArray.put(adminUser);
                
                // Add Test User (already approved)
                JSONObject testUser = new JSONObject();
                testUser.put("id", "user-001");
                testUser.put("username", "test");
                testUser.put("email", "test@gmail.com");
                testUser.put("password", "123456");
                testUser.put("isApproved", true);
                testUser.put("isAdmin", false);
                testUser.put("createdAt", System.currentTimeMillis());
                usersArray.put(testUser);
                
                prefs.edit()
                    .putString("users", usersArray.toString())
                    .putBoolean("initialized", true)
                    .apply();
                
                Log.d(TAG, "Sample data initialized: admin and test users created");
            } catch (Exception e) {
                Log.e(TAG, "Error initializing sample data", e);
            }
        }
    }

    public static MongoDBService getInstance(Context context) {
        if (instance == null) {
            instance = new MongoDBService(context);
        }
        return instance;
    }

    // Save current user to SharedPreferences
    public void saveCurrentUser(User user) {
        SharedPreferences prefs = context.getSharedPreferences("CoffeeClickPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("email", user.getEmail());
        editor.putBoolean("isAdmin", user.isAdmin());
        editor.putBoolean("isApproved", user.isApproved());
        editor.apply();
    }

    // Get current user from SharedPreferences
    public User getCurrentUser() {
        SharedPreferences prefs = context.getSharedPreferences("CoffeeClickPrefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("userId", null);
        if (userId == null) return null;

        User user = new User();
        user.setId(userId);
        user.setUsername(prefs.getString("username", ""));
        user.setEmail(prefs.getString("email", ""));
        user.setAdmin(prefs.getBoolean("isAdmin", false));
        user.setApproved(prefs.getBoolean("isApproved", false));
        return user;
    }

    // Clear current user (logout)
    public void clearCurrentUser() {
        SharedPreferences prefs = context.getSharedPreferences("CoffeeClickPrefs", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    // Register User (Simulated - stores in SharedPreferences)
    public interface RegisterCallback {
        void onSuccess(User user);
        void onError(String error);
    }

    public void registerUser(String username, String email, String password, RegisterCallback callback) {
        new Thread(() -> {
            try {
                // Simulate MongoDB insert
                User user = new User(username, email, password);
                user.setId(UUID.randomUUID().toString());
                user.setApproved(false); // Needs admin approval
                
                // Store in SharedPreferences (simulating MongoDB)
                SharedPreferences prefs = context.getSharedPreferences("UsersDB", Context.MODE_PRIVATE);
                String usersJson = prefs.getString("users", "[]");
                JSONArray usersArray = new JSONArray(usersJson);
                
                // Check if user already exists
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject userObj = usersArray.getJSONObject(i);
                    if (userObj.getString("username").equals(username) || 
                        userObj.getString("email").equals(email)) {
                        callback.onError("User already exists");
                        return;
                    }
                }
                
                // Add new user
                JSONObject newUser = new JSONObject();
                newUser.put("id", user.getId());
                newUser.put("username", user.getUsername());
                newUser.put("email", user.getEmail());
                newUser.put("password", user.getPassword());
                newUser.put("isApproved", user.isApproved());
                newUser.put("isAdmin", user.isAdmin());
                newUser.put("createdAt", user.getCreatedAt());
                
                usersArray.put(newUser);
                prefs.edit().putString("users", usersArray.toString()).apply();
                
                Log.d(TAG, "User registered: " + username);
                callback.onSuccess(user);
                
            } catch (Exception e) {
                Log.e(TAG, "Registration error", e);
                callback.onError(e.getMessage());
            }
        }).start();
    }

    // Login User
    public interface LoginCallback {
        void onSuccess(User user);
        void onError(String error);
    }

    public void loginUser(String username, String password, LoginCallback callback) {
        new Thread(() -> {
            try {
                SharedPreferences prefs = context.getSharedPreferences("UsersDB", Context.MODE_PRIVATE);
                String usersJson = prefs.getString("users", "[]");
                JSONArray usersArray = new JSONArray(usersJson);
                
                Log.d(TAG, "Login attempt - Username: " + username + ", Total users: " + usersArray.length());
                
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject userObj = usersArray.getJSONObject(i);
                    String dbUsername = userObj.getString("username");
                    String dbEmail = userObj.getString("email");
                    String dbPassword = userObj.getString("password");
                    
                    // Check if username/email matches and password matches
                    if ((dbUsername.equals(username) || dbEmail.equals(username)) && 
                        dbPassword.equals(password)) {
                        
                        User user = new User();
                        user.setId(userObj.getString("id"));
                        user.setUsername(userObj.getString("username"));
                        user.setEmail(userObj.getString("email"));
                        user.setPassword(userObj.getString("password"));
                        user.setApproved(userObj.getBoolean("isApproved"));
                        user.setAdmin(userObj.getBoolean("isAdmin"));
                        user.setCreatedAt(userObj.getLong("createdAt"));
                        
                        Log.d(TAG, "User found - Username: " + user.getUsername() + ", IsAdmin: " + user.isAdmin() + ", IsApproved: " + user.isApproved());
                        
                        if (!user.isApproved() && !user.isAdmin()) {
                            callback.onError("Account pending approval");
                            return;
                        }
                        
                        saveCurrentUser(user);
                        callback.onSuccess(user);
                        return;
                    }
                }
                
                Log.d(TAG, "Login failed - No matching user found");
                callback.onError("Invalid username or password");
                
            } catch (Exception e) {
                Log.e(TAG, "Login error", e);
                callback.onError("Login error: " + e.getMessage());
            }
        }).start();
    }

    // Save Order
    public interface SaveOrderCallback {
        void onSuccess(String orderId);
        void onError(String error);
    }

    public void saveOrder(Order order, SaveOrderCallback callback) {
        new Thread(() -> {
            try {
                order.setId(UUID.randomUUID().toString());
                order.setOrderDate(System.currentTimeMillis());
                
                SharedPreferences prefs = context.getSharedPreferences("OrdersDB", Context.MODE_PRIVATE);
                String ordersJson = prefs.getString("orders", "[]");
                JSONArray ordersArray = new JSONArray(ordersJson);
                
                Log.d(TAG, "Saving order - User: " + order.getUsername() + ", Items: " + order.getItems().size() + ", Total: " + order.getTotalAmount());
                
                JSONObject orderObj = new JSONObject(gson.toJson(order));
                ordersArray.put(orderObj);
                
                prefs.edit().putString("orders", ordersArray.toString()).apply();
                
                Log.d(TAG, "Order saved successfully - ID: " + order.getId() + ", Total orders: " + ordersArray.length());
                callback.onSuccess(order.getId());
                
            } catch (Exception e) {
                Log.e(TAG, "Save order error", e);
                callback.onError("Error: " + e.getMessage());
            }
        }).start();
    }

    // Get All Orders (Admin)
    public interface GetOrdersCallback {
        void onSuccess(List<Order> orders);
        void onError(String error);
    }

    public void getAllOrders(GetOrdersCallback callback) {
        new Thread(() -> {
            try {
                SharedPreferences prefs = context.getSharedPreferences("OrdersDB", Context.MODE_PRIVATE);
                String ordersJson = prefs.getString("orders", "[]");
                JSONArray ordersArray = new JSONArray(ordersJson);
                
                Log.d(TAG, "Getting all orders - Total in DB: " + ordersArray.length());
                Log.d(TAG, "Orders JSON: " + ordersJson);
                
                List<Order> orders = new ArrayList<>();
                for (int i = 0; i < ordersArray.length(); i++) {
                    JSONObject orderObj = ordersArray.getJSONObject(i);
                    Order order = gson.fromJson(orderObj.toString(), Order.class);
                    orders.add(order);
                    Log.d(TAG, "Order " + i + " - User: " + order.getUsername() + ", Total: " + order.getTotalAmount());
                }
                
                Log.d(TAG, "Returning " + orders.size() + " orders to callback");
                callback.onSuccess(orders);
                
            } catch (Exception e) {
                Log.e(TAG, "Get orders error", e);
                callback.onError("Error: " + e.getMessage());
            }
        }).start();
    }

    // Get All Users (Admin)
    public interface GetUsersCallback {
        void onSuccess(List<User> users);
        void onError(String error);
    }

    public void getAllUsers(GetUsersCallback callback) {
        new Thread(() -> {
            try {
                SharedPreferences prefs = context.getSharedPreferences("UsersDB", Context.MODE_PRIVATE);
                String usersJson = prefs.getString("users", "[]");
                JSONArray usersArray = new JSONArray(usersJson);
                
                List<User> users = new ArrayList<>();
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject userObj = usersArray.getJSONObject(i);
                    User user = new User();
                    user.setId(userObj.getString("id"));
                    user.setUsername(userObj.getString("username"));
                    user.setEmail(userObj.getString("email"));
                    user.setApproved(userObj.getBoolean("isApproved"));
                    user.setAdmin(userObj.getBoolean("isAdmin"));
                    user.setCreatedAt(userObj.getLong("createdAt"));
                    users.add(user);
                }
                
                callback.onSuccess(users);
                
            } catch (Exception e) {
                Log.e(TAG, "Get users error", e);
                callback.onError(e.getMessage());
            }
        }).start();
    }

    // Approve User (Admin)
    public interface ApproveUserCallback {
        void onSuccess();
        void onError(String error);
    }

    public void approveUser(String userId, boolean approve, ApproveUserCallback callback) {
        new Thread(() -> {
            try {
                SharedPreferences prefs = context.getSharedPreferences("UsersDB", Context.MODE_PRIVATE);
                String usersJson = prefs.getString("users", "[]");
                JSONArray usersArray = new JSONArray(usersJson);
                
                Log.d(TAG, "Approving user - ID: " + userId + ", Approve: " + approve);
                
                boolean found = false;
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject userObj = usersArray.getJSONObject(i);
                    if (userObj.getString("id").equals(userId)) {
                        userObj.put("isApproved", approve);
                        found = true;
                        Log.d(TAG, "User approval updated - Username: " + userObj.getString("username"));
                        break;
                    }
                }
                
                if (found) {
                    prefs.edit().putString("users", usersArray.toString()).apply();
                    Log.d(TAG, "Users database updated successfully");
                    callback.onSuccess();
                } else {
                    Log.e(TAG, "User not found with ID: " + userId);
                    callback.onError("User not found");
                }
                
            } catch (Exception e) {
                Log.e(TAG, "Approve user error", e);
                callback.onError("Error: " + e.getMessage());
            }
        }).start();
    }
}
