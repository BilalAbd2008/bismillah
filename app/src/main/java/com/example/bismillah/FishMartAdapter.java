package com.example.bismillah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class FishMartAdapter extends PagerAdapter {

    // Definisikan interface untuk klik produk
    public interface OnProductClickListener {
        void onProductClick(FishMartItem product);
    }

    private List<FishMartItem> productList;
    private Context context;
    private OnProductClickListener listener;

    // Constructor yang menerima listener
    public FishMartAdapter(List<FishMartItem> productList, Context context, OnProductClickListener listener) {
        this.productList = productList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        // Menghitung jumlah halaman berdasarkan dua produk per halaman
        return (int) Math.ceil(productList.size() / 2.0);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_fishmart, container, false); // Layout dengan dua CardView

        // Mengambil dua produk untuk halaman ini
        int firstProductIndex = position * 2;
        int secondProductIndex = firstProductIndex + 1;

        FishMartItem firstProduct = productList.get(firstProductIndex);
        FishMartItem secondProduct = null;
        if (secondProductIndex < productList.size()) {
            secondProduct = productList.get(secondProductIndex);
        }

        // Setup CardView kiri
        CardView cardViewLeft = view.findViewById(R.id.cardViewLeft);
        ImageView itemImageLeft = view.findViewById(R.id.itemImageLeft);
        TextView itemNameLeft = view.findViewById(R.id.itemNameLeft);
        TextView itemPriceLeft = view.findViewById(R.id.itemPriceLeft);

        itemImageLeft.setImageResource(firstProduct.getImageResId());
        itemNameLeft.setText(firstProduct.getName());
        itemPriceLeft.setText(firstProduct.getPrice());

        // Set OnClickListener untuk CardView kiri
        cardViewLeft.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(firstProduct);
            }
        });

        // Setup CardView kanan
        CardView cardViewRight = view.findViewById(R.id.cardViewRight);
        ImageView itemImageRight = view.findViewById(R.id.itemImageRight);
        TextView itemNameRight = view.findViewById(R.id.itemNameRight);
        TextView itemPriceRight = view.findViewById(R.id.itemPriceRight);

        if (secondProduct != null) {
            final FishMartItem finalSecondProduct = secondProduct; // Jadikan final
            itemImageRight.setImageResource(secondProduct.getImageResId());
            itemNameRight.setText(secondProduct.getName());
            itemPriceRight.setText(secondProduct.getPrice());

            // Set OnClickListener untuk CardView kanan
            cardViewRight.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProductClick(finalSecondProduct); // Menggunakan finalSecondProduct
                }
            });
        } else {
            // Sembunyikan CardView kanan jika tidak ada produk kedua
            cardViewRight.setVisibility(View.GONE);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
