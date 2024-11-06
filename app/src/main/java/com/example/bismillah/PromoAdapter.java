package com.example.bismillah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class PromoAdapter extends PagerAdapter {

    private List<Integer> promoImages;
    private LayoutInflater layoutInflater;
    private Context context;

    public PromoAdapter(List<Integer> promoImages, Context context) {
        this.promoImages = promoImages;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return promoImages.size(); // Jumlah gambar promo
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.promo_image_layout, container, false);

        ImageView imageView = view.findViewById(R.id.promoImageView);
        imageView.setImageResource(promoImages.get(position)); // Set gambar promo

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}


