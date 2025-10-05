package com.example.coffeeclick.models;

import com.example.coffeeclick.CartItem;
import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private String userId;
    private String username;
    private List<CartItem> items;
    private int totalAmount;
    private long orderDate;
    private String status; // "pending", "completed", "cancelled"

    public Order() {
        this.orderDate = System.currentTimeMillis();
        this.status = "pending";
    }

    public Order(String userId, String username, List<CartItem> items, int totalAmount) {
        this.userId = userId;
        this.username = username;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderDate = System.currentTimeMillis();
        this.status = "pending";
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
