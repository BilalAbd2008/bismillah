// File: TrainingAdapter.java
package com.example.bismillah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class TrainingAdapter extends PagerAdapter {

    private Context context;
    private List<TrainingModel> trainingList;
    private LayoutInflater layoutInflater;

    public TrainingAdapter(Context context, List<TrainingModel> trainingList) {
        this.context = context;
        this.trainingList = trainingList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // Jumlah halaman adalah ceil(trainingList.size() / 2.0)
        return (int) Math.ceil((double) trainingList.size() / 2);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.item_training_card, container, false);

        // Inisialisasi komponen untuk CardView pertama
        ImageView trainingImage1 = view.findViewById(R.id.trainingImage1);
        TextView trainingTitle1 = view.findViewById(R.id.trainingTitle1);
        TextView trainingPrice1 = view.findViewById(R.id.trainingPrice1);
        Button registerButton1 = view.findViewById(R.id.registerButton1);
        CardView cardView1 = view.findViewById(R.id.cardView1);

        // Inisialisasi komponen untuk CardView kedua
        ImageView trainingImage2 = view.findViewById(R.id.trainingImage2);
        TextView trainingTitle2 = view.findViewById(R.id.trainingTitle2);
        TextView trainingPrice2 = view.findViewById(R.id.trainingPrice2);
        Button registerButton2 = view.findViewById(R.id.registerButton2);
        CardView cardView2 = view.findViewById(R.id.cardView2);

        // Hitung indeks untuk setiap CardView
        int index1 = position * 2;
        int index2 = index1 + 1;

        // Assign data ke CardView pertama
        if (index1 < trainingList.size()) {
            TrainingModel training1 = trainingList.get(index1);

            trainingTitle1.setText(training1.getTitle());
            trainingPrice1.setText("Rp " + String.format("%,d", training1.getPrice()));

            Glide.with(context)
                    .load(training1.getImageResId())
                    .placeholder(R.drawable.tes1)
                    .into(trainingImage1);

            // Menetapkan OnClickListener pada CardView pertama
            cardView1.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("TITLE", training1.getTitle());
                intent.putExtra("AUTHOR", "Oleh Dzaky Ikbaar");
                intent.putExtra("PRICE", training1.getPrice());
                intent.putExtra("IMAGE_RES_ID", training1.getImageResId());
                context.startActivity(intent);
            });

            // Menetapkan OnClickListener pada Button pertama
            registerButton1.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("TITLE", training1.getTitle());
                intent.putExtra("AUTHOR", "Oleh Dzaky Ikbaar");
                intent.putExtra("PRICE", training1.getPrice());
                intent.putExtra("IMAGE_RES_ID", training1.getImageResId());
                context.startActivity(intent);
            });
        } else {
            // Jika tidak ada data, sembunyikan CardView
            cardView1.setVisibility(View.GONE);
        }

        // Assign data ke CardView kedua
        if (index2 < trainingList.size()) {
            TrainingModel training2 = trainingList.get(index2);

            trainingTitle2.setText(training2.getTitle());
            trainingPrice2.setText("Rp " + String.format("%,d", training2.getPrice()));

            Glide.with(context)
                    .load(training2.getImageResId())
                    .placeholder(R.drawable.tes2)
                    .into(trainingImage2);

            // Menetapkan OnClickListener pada CardView kedua
            cardView2.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("TITLE", training2.getTitle());
                intent.putExtra("AUTHOR", "Oleh Dzaky Ikbaar");
                intent.putExtra("PRICE", training2.getPrice());
                intent.putExtra("IMAGE_RES_ID", training2.getImageResId());
                context.startActivity(intent);
            });

            // Menetapkan OnClickListener pada Button kedua
            registerButton2.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("TITLE", training2.getTitle());
                intent.putExtra("AUTHOR", "Oleh Dzaky Ikbaar");
                intent.putExtra("PRICE", training2.getPrice());
                intent.putExtra("IMAGE_RES_ID", training2.getImageResId());
                context.startActivity(intent);
            });
        } else {
            // Jika tidak ada data, sembunyikan CardView
            cardView2.setVisibility(View.GONE);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
