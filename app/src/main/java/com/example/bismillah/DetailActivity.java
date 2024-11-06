// File: DetailActivity.java
package com.example.bismillah;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailImage;
    private TextView detailTitle, detailAuthor, detailPrice;
    private ImageButton backButton; // Tombol Kembali

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inisialisasi komponen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hapus title default
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        backButton = findViewById(R.id.backButton);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailAuthor = findViewById(R.id.detailAuthor);
        detailPrice = findViewById(R.id.detailPrice);

        // Mengatur listener untuk tombol kembali
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Menyelesaikan aktivitas dan kembali ke aktivitas sebelumnya
            }
        });

        // Mendapatkan data dari Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("TITLE");
            String author = extras.getString("AUTHOR");
            int price = extras.getInt("PRICE", 0);
            int imageResId = extras.getInt("IMAGE_RES_ID", 0);

            // Mengatur data ke komponen
            detailTitle.setText(title);
            detailAuthor.setText(author);
            detailPrice.setText("Rp " + String.format("%,d", price));

            // Memuat gambar menggunakan Glide
            Glide.with(this)
                    .load(imageResId)
                    .placeholder(R.drawable.tes1) // Pastikan drawable placeholder ada
                    .into(detailImage);
        }
    }
}
