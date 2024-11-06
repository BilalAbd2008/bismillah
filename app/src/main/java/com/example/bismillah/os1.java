package com.example.bismillah;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class os1 extends AppCompatActivity {
    Button  btnSkip;
    ImageButton btnNext;
    LinearLayout dotsLayout;
    TextView[] dots;  // Array untuk indikator titik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_os1);

        // Inisialisasi tombol
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        dotsLayout = findViewById(R.id.dotsLayout);  // Inisialisasi layout dots

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set indikator dots untuk halaman pertama (os1)
        addDotsIndicator(0);  // Halaman pertama = posisi 0

        // Skip button click listener
        btnSkip.setOnClickListener(v -> {
            Intent intent = new Intent(os1.this, login.class);
            startActivity(intent);
            finish();
        });

        // Next button click listener
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(os1.this, os2.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });
    }

    // Method untuk menambahkan indikator dots
    private void addDotsIndicator(int position) {
        dotsLayout.removeAllViews();  // Bersihkan semua titik sebelumnya
        dots = new TextView[3];  // Misal ada 3 halaman onboarding (os1, os2, os3)

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setTextSize(35);
            dots[i].setText(android.text.Html.fromHtml("&#8226;"));  // Unicode untuk titik (•)
            dots[i].setTextColor(getResources().getColor(R.color.colorInactiveDot));  // Warna default untuk titik tidak aktif
            dotsLayout.addView(dots[i]);  // Tambahkan titik ke layout
        }

        // Aktifkan titik pada posisi saat ini
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorActiveDot));  // Warna titik aktif
        }
    }
}
