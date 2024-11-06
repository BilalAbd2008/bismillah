package com.example.bismillah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemChangeListener {

    private RecyclerView cartRecyclerView;
    private TextView totalPriceText, totalItemCount;
    private Button checkoutButton;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart); // Menggunakan layout yang telah diperbarui

        // Inisialisasi view
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceText = findViewById(R.id.totalPriceText);
        totalItemCount = findViewById(R.id.totalItemCount);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Ambil item keranjang dari CartManager
        cartItems = CartManager.getInstance().getCartItems();

        // Setup RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CartAdapter adapter = new CartAdapter(cartItems, this, this); // Pass listener
        cartRecyclerView.setAdapter(adapter);

        // Update total harga dan jumlah item secara awal
        updateTotalPriceAndCount();

        // Tombol checkout
        checkoutButton.setOnClickListener(v -> proceedToCheckout());
    }

    private void updateTotalPriceAndCount() {
        int totalPrice = 0;
        int totalCount = 0;

        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
            totalCount += item.getQuantity();
        }

        totalPriceText.setText("Total: Rp" + totalPrice);
        totalItemCount.setText("Total item: " + totalCount);
    }

    @Override
    public void onItemChanged() {
        updateTotalPriceAndCount(); // Update totals whenever an item changes
    }

    private void proceedToCheckout() {
        Intent intent = new Intent(CartActivity.this, CheckOutActivity.class);
        startActivity(intent); // Navigasi ke activity checkout
    }
}
