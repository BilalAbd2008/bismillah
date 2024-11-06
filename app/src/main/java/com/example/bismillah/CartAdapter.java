package com.example.bismillah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private Context context;
    private OnCartItemChangeListener itemChangeListener; // Interface for item change callback

    // Constructor to initialize cart items and context
    public CartAdapter(List<CartItem> cartItems, Context context, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.context = context;
        this.itemChangeListener = listener; // Set the listener
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem currentItem = cartItems.get(position);

        // Set the data for each item in the cart
        holder.itemName.setText(currentItem.getName());
        holder.itemPrice.setText("Rp" + currentItem.getPrice());
        holder.itemQuantity.setText(String.valueOf(currentItem.getQuantity()));
        holder.itemImage.setImageResource(currentItem.getImageResourceId());

        // Handle quantity update logic
        holder.addButton.setOnClickListener(v -> {
            currentItem.incrementQuantity(); // Increment quantity in the CartItem object
            holder.itemQuantity.setText(String.valueOf(currentItem.getQuantity())); // Update displayed quantity
            itemChangeListener.onItemChanged(); // Notify item change
        });

        holder.subtractButton.setOnClickListener(v -> {
            if (currentItem.getQuantity() > 1) {
                currentItem.decrementQuantity(); // Decrement quantity in the CartItem object
                holder.itemQuantity.setText(String.valueOf(currentItem.getQuantity())); // Update displayed quantity
                itemChangeListener.onItemChanged(); // Notify item change
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemQuantity;
        ImageView itemImage;
        ImageButton addButton, subtractButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            addButton = itemView.findViewById(R.id.addButton);
            subtractButton = itemView.findViewById(R.id.subtractButton);
        }
    }

    // Interface to handle item changes
    public interface OnCartItemChangeListener {
        void onItemChanged();
    }
}
