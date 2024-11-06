// File: PelatihanAdapter.java
package com.example.bismillah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide; // Pastikan Anda telah menambahkan dependensi Glide

import java.util.List;

public class PelatihanAdapter extends PagerAdapter {

    private Context context;
    private List<Pelatihan> pelatihanList;
    private OnItemClickListener listener;
    private LayoutInflater layoutInflater;

    // Interface untuk menangani klik pada item
    public interface OnItemClickListener {
        void onItemClick(Pelatihan pelatihan);
    }

    // Konstruktor
    public PelatihanAdapter(Context context, List<Pelatihan> pelatihanList, OnItemClickListener listener) {
        this.context = context;
        this.pelatihanList = pelatihanList;
        this.listener = listener;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // Setiap halaman menampilkan dua item
        return (pelatihanList.size() + 1) / 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate layout halaman ViewPager
        View view = layoutInflater.inflate(R.layout.item_pelatihan, container, false);

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

        // Mendapatkan data pelatihan pertama berdasarkan posisi
        int index1 = position * 2;
        if (index1 < pelatihanList.size()) {
            Pelatihan pelatihan1 = pelatihanList.get(index1);
            trainingTitle1.setText(pelatihan1.getTitle());
            trainingPrice1.setText("Rp " + String.format("%,d", pelatihan1.getPrice()));

            // Memuat gambar menggunakan Glide dengan imageResId
            Glide.with(context)
                    .load(pelatihan1.getImageResId())
                    .placeholder(R.drawable.tes1) // Pastikan drawable placeholder ada
                    .into(trainingImage1);

            // Menangani klik pada tombol detail untuk CardView pertama
            registerButton1.setOnClickListener(v -> listener.onItemClick(pelatihan1));

            // Menangani klik pada CardView pertama
            cardView1.setOnClickListener(v -> listener.onItemClick(pelatihan1));
        } else {
            // Jika tidak ada data, sembunyikan CardView pertama
            cardView1.setVisibility(View.GONE);
        }

        // Mendapatkan data pelatihan kedua berdasarkan posisi
        int index2 = position * 2 + 1;
        if (index2 < pelatihanList.size()) {
            Pelatihan pelatihan2 = pelatihanList.get(index2);
            trainingTitle2.setText(pelatihan2.getTitle());
            trainingPrice2.setText("Rp " + String.format("%,d", pelatihan2.getPrice()));

            // Memuat gambar menggunakan Glide dengan imageResId
            Glide.with(context)
                    .load(pelatihan2.getImageResId())
                    .placeholder(R.drawable.tes1) // Pastikan drawable placeholder ada
                    .into(trainingImage2);

            // Menangani klik pada tombol detail untuk CardView kedua
            registerButton2.setOnClickListener(v -> listener.onItemClick(pelatihan2));

            // Menangani klik pada CardView kedua
            cardView2.setOnClickListener(v -> listener.onItemClick(pelatihan2));
        } else {
            // Jika tidak ada data, sembunyikan CardView kedua
            cardView2.setVisibility(View.GONE);
        }

        // Menambahkan view ke container
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // Menghapus view dari container
        container.removeView((View) object);
    }
}
