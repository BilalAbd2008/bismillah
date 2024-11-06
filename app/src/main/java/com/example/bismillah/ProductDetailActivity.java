package com.example.bismillah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productName, productPrice, productDescription;
    private ImageButton backButton, cartButton;
    private Button buyButton;
    private int imageResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Initialize views
        productImageView = findViewById(R.id.productImageView);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        backButton = findViewById(R.id.backButton);
        buyButton = findViewById(R.id.buyButton);
        cartButton = findViewById(R.id.cartButton);

        // Get data from Intent
        imageResourceId = getIntent().getIntExtra("imageResourceId", 0);
        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");

        // Set data to views
        productImageView.setImageResource(imageResourceId);
        productName.setText(name);
        productPrice.setText(price);
        productDescription.setText(description);

        backButton.setOnClickListener(v -> onBackPressed());

        // Buy button action
        buyButton.setOnClickListener(v -> showBuyPopup(imageResourceId, name, price));

        // Cart button action
        cartButton.setOnClickListener(v -> {
            addToCart(); // Add product to the cart without popup
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent); // Navigate to CartActivity
        });
    }

    private void showBuyPopup(int imageResId, String name, String price) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_buy, null);
        builder.setView(dialogView);

        // Setup views in the popup
        ImageView productImage = dialogView.findViewById(R.id.productImage);
        TextView productName = dialogView.findViewById(R.id.productName);
        TextView productPrice = dialogView.findViewById(R.id.productPrice);
        TextView quantityTextView = dialogView.findViewById(R.id.quantityTextView);
        ImageButton addButton = dialogView.findViewById(R.id.addButton);
        ImageButton subtractButton = dialogView.findViewById(R.id.subtractButton);
        Button orderButton = dialogView.findViewById(R.id.orderNowButton);

        // Set the product image, name, and price
        productImage.setImageResource(imageResId);
        productName.setText(name);
        productPrice.setText(price);

        // Manage quantity in the popup
        addButton.setOnClickListener(v -> {
            // Increment quantity logic (if you still want it)
        });

        subtractButton.setOnClickListener(v -> {
            // Decrement quantity logic (if you still want it)
        });

        orderButton.setOnClickListener(v -> {
            Toast.makeText(ProductDetailActivity.this, "Order placed", Toast.LENGTH_SHORT).show();
            proceedToCheckout();
        });

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addToCart() {
        // Convert the price to an integer
        int price = Integer.parseInt(productPrice.getText().toString().replace("Rp", "").replace(".", "").trim());

        // Create the CartItem
        CartItem cartItem = new CartItem(imageResourceId, productName.getText().toString(), price, 1); // Set quantity to 1

        // Add the cart item to CartManager
        CartManager.getInstance().addItem(cartItem);
        Toast.makeText(ProductDetailActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
    }


    private void proceedToCheckout() {
        Intent intent = new Intent(ProductDetailActivity.this, CheckOutActivity.class);
        intent.putExtra("product_name", productName.getText().toString());
        intent.putExtra("price", productPrice.getText().toString());
        startActivity(intent);
    }
}
