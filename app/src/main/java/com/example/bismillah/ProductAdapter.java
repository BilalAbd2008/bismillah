package com.example.bismillah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fishmart, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Setup produk pertama (cardViewLeft)
        Product firstProduct = productList.get(position * 2); // Dapatkan produk pertama
        holder.itemImageLeft.setImageResource(firstProduct.getImageResourceId());
        holder.itemNameLeft.setText(firstProduct.getName());
        holder.itemPriceLeft.setText(firstProduct.getPrice());

        // Ketika cardViewLeft ditekan, buka ProductDetailActivity
        holder.cardViewLeft.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("imageResourceId", firstProduct.getImageResourceId());
            intent.putExtra("name", firstProduct.getName());
            intent.putExtra("price", firstProduct.getPrice());
            intent.putExtra("description", firstProduct.getDescription());
            context.startActivity(intent);
        });

        // Setup produk kedua (cardViewRight), jika ada
        if ((position * 2 + 1) < productList.size()) {
            Product secondProduct = productList.get(position * 2 + 1); // Dapatkan produk kedua
            holder.itemImageRight.setImageResource(secondProduct.getImageResourceId());
            holder.itemNameRight.setText(secondProduct.getName());
            holder.itemPriceRight.setText(secondProduct.getPrice());

            // Ketika cardViewRight ditekan, buka ProductDetailActivity
            holder.cardViewRight.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("imageResourceId", secondProduct.getImageResourceId());
                intent.putExtra("name", secondProduct.getName());
                intent.putExtra("price", secondProduct.getPrice());
                intent.putExtra("description", secondProduct.getDescription());
                context.startActivity(intent);
            });
        } else {
            // Sembunyikan cardViewRight jika tidak ada produk kedua
            holder.cardViewRight.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        // Karena dua produk per halaman, kita hitung jumlah halaman
        return (int) Math.ceil(productList.size() / 2.0);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageLeft, itemImageRight;
        TextView itemNameLeft, itemNameRight, itemPriceLeft, itemPriceRight;
        CardView cardViewLeft, cardViewRight;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inisialisasi elemen-elemen di cardViewLeft
            itemImageLeft = itemView.findViewById(R.id.itemImageLeft);
            itemNameLeft = itemView.findViewById(R.id.itemNameLeft);
            itemPriceLeft = itemView.findViewById(R.id.itemPriceLeft);
            cardViewLeft = itemView.findViewById(R.id.cardViewLeft);

            // Inisialisasi elemen-elemen di cardViewRight
            itemImageRight = itemView.findViewById(R.id.itemImageRight);
            itemNameRight = itemView.findViewById(R.id.itemNameRight);
            itemPriceRight = itemView.findViewById(R.id.itemPriceRight);
            cardViewRight = itemView.findViewById(R.id.cardViewRight);
        }
    }
}
