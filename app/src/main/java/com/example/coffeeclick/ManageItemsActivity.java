package com.example.coffeeclick;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageItemsActivity extends AppCompatActivity {

    EditText etCoffeeName, etCoffeePrice, etCoffeeDescription, etRating;
    Button btnAddItem, btnBack, btnSelectImage, btnSelectCategory, btnViewItems;
    ImageView ivPreview;
    int selectedImageResource = R.drawable.espresso; // Default
    Uri selectedImageUri = null;
    String selectedCategory = "Hot"; // Default

    // Image picker launcher
    ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);

        // Initialize database with context
        CoffeeDatabase.init(this);

        etCoffeeName = findViewById(R.id.etCoffeeName);
        etCoffeePrice = findViewById(R.id.etCoffeePrice);
        etCoffeeDescription = findViewById(R.id.etCoffeeDescription);
        etRating = findViewById(R.id.etRating);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnBack = findViewById(R.id.btnBack);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSelectCategory = findViewById(R.id.btnSelectCategory);
        btnViewItems = findViewById(R.id.btnViewItems);
        ivPreview = findViewById(R.id.ivPreview);
        
        btnBack.setOnClickListener(v -> finish());
        
        // View items button
        btnViewItems.setOnClickListener(v -> {
            Intent intent = new Intent(ManageItemsActivity.this, ViewItemsActivity.class);
            startActivity(intent);
        });
        
        // Category selection button
        btnSelectCategory.setText("Category: " + selectedCategory);
        btnSelectCategory.setOnClickListener(v -> showCategoryDialog());

        // Initialize image picker
        imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivPreview.setImageURI(selectedImageUri);
                    Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
                }
            }
        );

        // Select image button
        btnSelectImage.setOnClickListener(v -> showImageSelectionDialog());

        btnAddItem.setOnClickListener(v -> {
            String name = etCoffeeName.getText().toString().trim();
            String priceStr = etCoffeePrice.getText().toString().trim();
            String description = etCoffeeDescription.getText().toString().trim();
            String ratingStr = etRating.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int price = Integer.parseInt(priceStr);
                float rating = ratingStr.isEmpty() ? 4.5f : Float.parseFloat(ratingStr);
                
                // Validate rating
                if (rating < 1.0f || rating > 5.0f) {
                    Toast.makeText(this, "Rating must be between 1.0 and 5.0", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                // Use selected image or default
                int imageRes = selectedImageResource;
                if (selectedImageUri != null) {
                    // For now, use default image (URI handling requires more setup)
                    imageRes = R.drawable.espresso;
                }
                
                // Create coffee with category and rating
                Coffee newCoffee = new Coffee(name, price, imageRes, description, selectedCategory, true, rating);
                
                // Add to database (shared across app)
                CoffeeDatabase.addCoffee(newCoffee);

                // Clear fields
                etCoffeeName.setText("");
                etCoffeePrice.setText("");
                etCoffeeDescription.setText("");
                etRating.setText("");
                selectedImageUri = null;
                selectedCategory = "Hot";
                btnSelectCategory.setText("Category: Hot");
                ivPreview.setImageResource(R.drawable.espresso);

                Toast.makeText(this, "Item added to database: " + name + " (" + selectedCategory + ")", Toast.LENGTH_LONG).show();
                android.util.Log.d("ManageItems", "Added to database: " + name + " | Category: " + selectedCategory + " | Rating: " + rating);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price or rating", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showImageSelectionDialog() {
        String[] options = {"Choose from Gallery", "Select Default Image"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image Source");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                // Open gallery
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                imagePickerLauncher.launch(intent);
            } else {
                // Show default images
                showDefaultImagesDialog();
            }
        });
        builder.show();
    }

    private void showDefaultImagesDialog() {
        String[] imageNames = {"Espresso", "Cappuccino", "Latte"};
        int[] imageResources = {R.drawable.espresso, R.drawable.cappuccino, R.drawable.latte};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Default Image");
        builder.setItems(imageNames, (dialog, which) -> {
            selectedImageResource = imageResources[which];
            ivPreview.setImageResource(selectedImageResource);
            Toast.makeText(this, imageNames[which] + " image selected", Toast.LENGTH_SHORT).show();
        });
        builder.show();
    }
    
    private void showCategoryDialog() {
        String[] categories = {"Hot", "Cold", "Dessert", "Special"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category");
        builder.setItems(categories, (dialog, which) -> {
            selectedCategory = categories[which];
            btnSelectCategory.setText("Category: " + selectedCategory);
            Toast.makeText(this, selectedCategory + " category selected", Toast.LENGTH_SHORT).show();
            android.util.Log.d("ManageItems", "Category selected: " + selectedCategory);
        });
        builder.show();
    }
}
