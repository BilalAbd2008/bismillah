package com.example.bismillah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FishCategoryActivity extends AppCompatActivity {

    private RecyclerView fishRecyclerView;
    private FishTypeAdapter fishTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_category);

        TextView categoryTitle = findViewById(R.id.categoryTitle);
        String categoryName = getIntent().getStringExtra("categoryName");
        categoryTitle.setText(categoryName);

        fishRecyclerView = findViewById(R.id.fishRecyclerView);
        fishRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load fish data based on category
        List<FishType> fishTypes = loadFishData(categoryName);
        fishTypeAdapter = new FishTypeAdapter(fishTypes, this);
        fishRecyclerView.setAdapter(fishTypeAdapter);
    }

    private List<FishType> loadFishData(String categoryName) {
        List<FishType> fishTypes = new ArrayList<>();

        // Populate fishTypes based on category (this is just an example)
        if (categoryName.equals("Koi")) {
            fishTypes.add(new FishType(R.drawable.koi_tancho, "Koi Tancho", "Air Tawar"));
            fishTypes.add(new FishType(R.drawable.koi_chagoi, "Koi Chagoi", "Air Tawar"));
            fishTypes.add(new FishType(R.drawable.koi_sanke, "Koi Sanke", "Air Tawar"));
            fishTypes.add(new FishType(R.drawable.koi_slayer, "Koi Slayer", "Air Tawar"));
            // Add more fish types as needed
        }   else if (categoryName.equals("Cupang")) {
            fishTypes.add(new FishType(R.drawable.cupang_betta, "Cupang Betta", "Air Tawar"));
            fishTypes.add(new FishType(R.drawable.cupang_halfmoon, "Cupang Halfmoon", "Air Tawar"));
            fishTypes.add(new FishType(R.drawable.cupang_veil, "Cupang Veil", "Air Tawar"));
        }   else if (categoryName.equals("Air Laut")) {
            fishTypes.add(new FishType(R.drawable.ikan_clownfish, "Clownfish", "Air Laut"));
            fishTypes.add(new FishType(R.drawable.ikan_lion_fish, "Lion Fish", "Air Laut"));
            fishTypes.add(new FishType(R.drawable.ikan_butterflyfish, "Butterflyfish", "Air Laut"));
        }    else if (categoryName.equals("Air Tawar")) {
            fishTypes.add(new FishType(R.drawable.ikan_guppy, "Guppy", "Air Tawar"));
            fishTypes.add(new FishType(R.drawable.ikan_molly, "Molly", "Air Tawar"));
            // Tambahkan lebih banyak tanaman jika diperlukan
        }

        return fishTypes;
    }
}
