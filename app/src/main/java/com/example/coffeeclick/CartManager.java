package com.example.coffeeclick;

import java.util.HashMap;
import java.util.Map;

public class CartManager {

    private static CartManager instance;
    private final Map<Coffee, Integer> cartItems;

    private CartChangeListener listener;

    public interface CartChangeListener {
        void onCartUpdated(int totalItems);
    }

    private CartManager() {
        cartItems = new HashMap<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void setCartChangeListener(CartChangeListener listener) {
        this.listener = listener;
    }

    public void addItem(Coffee coffee, int quantity) {
        if (cartItems.containsKey(coffee)) {
            cartItems.put(coffee, cartItems.get(coffee) + quantity);
        } else {
            cartItems.put(coffee, quantity);
        }
        notifyListener();
    }

    public void removeItem(Coffee coffee) {
        cartItems.remove(coffee);
        notifyListener();
    }

    public void clearCart() {
        cartItems.clear();
        notifyListener();
    }

    public Map<Coffee, Integer> getCartItems() {
        return cartItems;
    }

    public int getTotalItems() {
        int total = 0;
        for (int quantity : cartItems.values()) {
            total += quantity;
        }
        return total;
    }
    
    public int getCartSize() {
        int total = 0;
        for (int quantity : cartItems.values()) {
            total += quantity;
        }
        return total;
    }

    private void notifyListener() {
        if (listener != null) {
            listener.onCartUpdated(getTotalItems());
        }
    }
}
