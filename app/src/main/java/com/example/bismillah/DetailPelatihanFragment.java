// File: DetailPelatihanFragment.java
package com.example.bismillah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class DetailPelatihanFragment extends Fragment {

    private TextView detailTitle, detailPrice, Description;
    private ImageView detailImage;
    private ImageButton backButton; // Tombol Kembali

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_pelatihan, container, false);

        // Inisialisasi komponen
        backButton = view.findViewById(R.id.backButton);
        detailTitle = view.findViewById(R.id.detailTitle);
        detailPrice = view.findViewById(R.id.detailPrice);
        detailImage = view.findViewById(R.id.detailImage);
        Description = view.findViewById(R.id.Detailsdeskripsi);

        // Mengatur listener untuk tombol kembali
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Mengembalikan pengguna ke PelatihanFragment dengan menghapus fragment saat ini dari back stack
                if (getParentFragmentManager() != null) {
                    getParentFragmentManager().popBackStack();
                }
            }
        });

        // Mendapatkan data dari arguments
        Bundle args = getArguments();
        if (args != null) {
            Pelatihan pelatihan = args.getParcelable("pelatihan");
            if (pelatihan != null) {
                detailTitle.setText(pelatihan.getTitle());
                detailPrice.setText("Rp " + String.format("%,d", pelatihan.getPrice()));

                // Memuat gambar menggunakan Glide dengan imageResId
                Glide.with(this)
                        .load(pelatihan.getImageResId())
                        .placeholder(R.drawable.tes1) // Pastikan drawable placeholder ada
                        .into(detailImage);
            }
        }

        return view;
    }
}
