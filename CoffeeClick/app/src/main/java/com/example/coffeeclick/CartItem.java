package com.example.coffeeclick;

public class CartItem {
    private Coffee coffee;
    private int quantity;

    public CartItem(Coffee coffee, int quantity) {
        this.coffee = coffee;
        this.quantity = quantity;
    }

    // This getter is required for CartAdapter and CartActivity
    public Coffee getCoffee() {
        return coffee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
