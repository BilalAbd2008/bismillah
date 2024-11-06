package com.example.bismillah;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bismillah.databinding.ActivityHomepageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Homepage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    ActivityHomepageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Mengatur layout
        replaceFragment(new HomeFragment()); // Fragment default saat aplikasi dibuka

        // Menggunakan findViewById untuk mengakses BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frame_layout);

        // Listener untuk handle item yang dipilih
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Tentukan fragment yang dipilih berdasarkan item menggunakan if-else
                if (item.getItemId() == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (item.getItemId() == R.id.fish) {
                    replaceFragment(new FishPediaFragment());
                } else if (item.getItemId() == R.id.market) {
                    replaceFragment(new FishMartFragment());
                } else if (item.getItemId() == R.id.profile) {
                    replaceFragment(new PelatihanFragment());
                }
                return true; // Pastikan untuk mengembalikan true untuk menunjukkan bahwa item telah dipilih
            }
        });
    }

    // Fungsi untuk mengganti fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
