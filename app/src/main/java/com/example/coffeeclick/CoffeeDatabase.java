package com.example.coffeeclick;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CoffeeDatabase {

    // Static list to hold all coffees (shared across app)
    private static List<Coffee> coffeeList = null;
    private static final String PREFS_NAME = "CoffeePrefs";
    private static final String KEY_CUSTOM_COFFEES = "custom_coffees";
    private static Context appContext;
    
    // Initialize context
    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }
    
    // Initialize database with default items
    private static void initializeDatabase() {
        if (coffeeList != null) {
            return; // Already initialized
        }
        
        coffeeList = new ArrayList<>();

        // Popular Coffees
        coffeeList.add(new Coffee(
            "Espresso",
            350,
            R.drawable.espresso,
            "A concentrated shot of pure coffee perfection. Rich, bold, and intensely aromatic. Perfect for a quick energy boost or as a base for other drinks.",
            "Hot",
            true,  // isPopular
            4.8f   // rating
        ));

        coffeeList.add(new Coffee(
            "Cappuccino",
            450,
            R.drawable.cappuccino,
            "A harmonious blend of espresso, steamed milk, and velvety foam. Topped with a dusting of cocoa powder. Creamy, smooth, and perfectly balanced.",
            "Hot",
            true,
            4.7f
        ));

        coffeeList.add(new Coffee(
            "Latte",
            400,
            R.drawable.latte,
            "Smooth and milky with a gentle coffee flavor. Made with espresso and lots of steamed milk. Perfect for those who prefer a milder coffee experience.",
            "Hot",
            true,
            4.6f
        ));

        coffeeList.add(new Coffee(
            "Americano",
            300,
            R.drawable.espresso,
            "Bold espresso diluted with hot water. Strong, smooth, and less intense than straight espresso. A classic choice for coffee purists.",
            "Hot",
            true,
            4.5f
        ));

        coffeeList.add(new Coffee(
            "Mocha",
            500,
            R.drawable.cappuccino,
            "A delightful fusion of rich espresso, steamed milk, and premium chocolate. Topped with whipped cream. The perfect treat for chocolate lovers!",
            "Hot",
            true,
            4.9f
        ));

        coffeeList.add(new Coffee(
            "Macchiato",
            380,
            R.drawable.espresso,
            "Espresso 'marked' with a dollop of foamed milk. Strong coffee flavor with a hint of creaminess. For those who like it bold but not bitter.",
            "Hot",
            false,
            4.4f
        ));

        coffeeList.add(new Coffee(
            "Flat White",
            420,
            R.drawable.latte,
            "Velvety microfoam poured over a double shot of espresso. Smooth, strong, and perfectly textured. A favorite among coffee connoisseurs.",
            "Hot",
            true,
            4.7f
        ));

        coffeeList.add(new Coffee(
            "Iced Coffee",
            350,
            R.drawable.latte,
            "Refreshing cold brew served over ice. Smooth, less acidic, and naturally sweet. Perfect for hot days or when you need a cool caffeine fix.",
            "Cold",
            true,
            4.6f
        ));

        coffeeList.add(new Coffee(
            "Iced Latte",
            420,
            R.drawable.latte,
            "Chilled espresso mixed with cold milk and ice. Creamy, refreshing, and perfectly balanced. A cool twist on the classic latte.",
            "Cold",
            true,
            4.5f
        ));

        coffeeList.add(new Coffee(
            "Caramel Macchiato",
            480,
            R.drawable.cappuccino,
            "Vanilla-flavored latte marked with espresso and finished with caramel drizzle. Sweet, creamy, and utterly indulgent.",
            "Hot",
            true,
            4.8f
        ));

        coffeeList.add(new Coffee(
            "Affogato",
            450,
            R.drawable.espresso,
            "A scoop of vanilla ice cream 'drowned' in a shot of hot espresso. The perfect marriage of coffee and dessert. Simply divine!",
            "Dessert",
            false,
            4.9f
        ));

        coffeeList.add(new Coffee(
            "Irish Coffee",
            550,
            R.drawable.cappuccino,
            "Hot coffee mixed with Irish whiskey and sugar, topped with cream. A warming, spirited drink perfect for cold evenings.",
            "Special",
            false,
            4.6f
        ));
        
        android.util.Log.d("CoffeeDatabase", "Database initialized with " + coffeeList.size() + " default items");
        
        // Load custom coffees from SharedPreferences
        loadCustomCoffees();
    }
    
    // Load custom coffees from SharedPreferences
    private static void loadCustomCoffees() {
        if (appContext == null) {
            android.util.Log.e("CoffeeDatabase", "Context not initialized! Call CoffeeDatabase.init(context) first");
            return;
        }
        
        SharedPreferences prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_CUSTOM_COFFEES, null);
        
        if (json != null) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Coffee>>(){}.getType();
                List<Coffee> customCoffees = gson.fromJson(json, type);
                
                if (customCoffees != null && !customCoffees.isEmpty()) {
                    coffeeList.addAll(customCoffees);
                    android.util.Log.d("CoffeeDatabase", "Loaded " + customCoffees.size() + " custom coffees from storage");
                }
            } catch (Exception e) {
                android.util.Log.e("CoffeeDatabase", "Error loading custom coffees: " + e.getMessage());
            }
        }
        
        android.util.Log.d("CoffeeDatabase", "Total items after loading: " + coffeeList.size());
    }
    
    // Save custom coffees to SharedPreferences
    private static void saveCustomCoffees() {
        if (appContext == null) {
            android.util.Log.e("CoffeeDatabase", "Context not initialized!");
            return;
        }
        
        // Get only custom coffees (items added after the initial 12)
        List<Coffee> customCoffees = new ArrayList<>();
        if (coffeeList.size() > 12) {
            customCoffees = coffeeList.subList(12, coffeeList.size());
        }
        
        SharedPreferences prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        Gson gson = new Gson();
        String json = gson.toJson(customCoffees);
        editor.putString(KEY_CUSTOM_COFFEES, json);
        editor.apply();
        
        android.util.Log.d("CoffeeDatabase", "Saved " + customCoffees.size() + " custom coffees to storage");
    }
    
    public static List<Coffee> getAllCoffees() {
        initializeDatabase();
        return new ArrayList<>(coffeeList); // Return copy to prevent external modification
    }
    
    // Method to add new coffee item (for admin)
    public static void addCoffee(Coffee coffee) {
        initializeDatabase();
        coffeeList.add(coffee);
        saveCustomCoffees(); // Save to persistent storage
        android.util.Log.d("CoffeeDatabase", "Added coffee: " + coffee.getName() + " | Total items: " + coffeeList.size());
    }
    
    // Method to remove coffee item (for admin)
    public static void removeCoffee(Coffee coffee) {
        initializeDatabase();
        coffeeList.remove(coffee);
        saveCustomCoffees(); // Save to persistent storage
        android.util.Log.d("CoffeeDatabase", "Removed coffee: " + coffee.getName() + " | Total items: " + coffeeList.size());
    }

    public static List<Coffee> getPopularCoffees() {
        List<Coffee> allCoffees = getAllCoffees();
        List<Coffee> popularCoffees = new ArrayList<>();
        
        for (Coffee coffee : allCoffees) {
            if (coffee.isPopular()) {
                popularCoffees.add(coffee);
            }
        }
        
        return popularCoffees;
    }

    public static List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("All");
        categories.add("Hot");
        categories.add("Cold");
        categories.add("Dessert");
        categories.add("Special");
        return categories;
    }

    public static List<Coffee> getCoffeesByCategory(String category) {
        if (category.equals("All")) {
            return getAllCoffees();
        }
        
        List<Coffee> allCoffees = getAllCoffees();
        List<Coffee> filtered = new ArrayList<>();
        
        for (Coffee coffee : allCoffees) {
            if (coffee.getCategory().equals(category)) {
                filtered.add(coffee);
            }
        }
        
        return filtered;
    }
}
