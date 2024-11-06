package com.example.bismillah;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckOutActivity extends AppCompatActivity {

    private TextView productNameTextView, totalPriceTextView, quantityTextView;
    private double pricePerItem;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        // Initialize views
        productNameTextView = findViewById(R.id.productNameTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        quantityTextView = findViewById(R.id.quantityTextView);
        backButton = findViewById(R.id.balik);

        // Get data from Intent
        String productName = getIntent().getStringExtra("product_name");
        String price = getIntent().getStringExtra("price");
        int quantity = getIntent().getIntExtra("quantity", 1);

        // Remove currency symbol from price and convert to double
        pricePerItem = Double.parseDouble(price.replace("Rp", "").replace(".", "").trim());

        // Set data to views
        productNameTextView.setText(productName);
        quantityTextView.setText(String.valueOf(quantity));

        // Calculate total price and display it
        double totalPrice = pricePerItem * quantity;
        totalPriceTextView.setText("Rp " + String.format("%,.0f", totalPrice));

        backButton.setOnClickListener(v -> onBackPressed());
    }
}
