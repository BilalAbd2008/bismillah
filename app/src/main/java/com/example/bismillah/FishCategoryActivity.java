package com.example.bismillah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FishCategoryActivity extends AppCompatActivity {

    private RecyclerView fishRecyclerView;
    private FishTypeAdapter fishTypeAdapter;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_category);

        // Initialize back button
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ini akan menutup activity saat ini dan kembali ke activity sebelumnya
                finish();
            }
        });

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
            fishTypes.add(new FishType(1, "Koi Tancho", "Air Tawar", R.drawable.koi_tancho));
            fishTypes.add(new FishType(2, "Koi Chagoi", "Air Tawar", R.drawable.koi_chagoi));
            fishTypes.add(new FishType(3, "Koi Kohaku", "Air Tawar", R.drawable.koi_slayer));
            fishTypes.add(new FishType(4, "Koi Sanke", "Air Tawar", R.drawable.koi_sanke));
            // Add more fish types as needed
        }   else if (categoryName.equals("Cupang")) {
            fishTypes.add(new FishType(5, "Cupang Betta","Air Tawar", R.drawable.cupang_betta));
            fishTypes.add(new FishType(7, "Cupang Halfmoon", "Air Tawar", R.drawable.cupang_halfmoon));
            fishTypes.add(new FishType(8,"Cupang Veil" , "Air Tawar", R.drawable.cupang_veil));
        }   else if (categoryName.equals("Air Laut")) {
            fishTypes.add(new FishType(9, "Clownfish","Air Laut" , R.drawable.ikan_clownfish));
            fishTypes.add(new FishType(10, "Lion Fish","Air Laut" , R.drawable.ikan_lion_fish));
            fishTypes.add(new FishType(11, "Butterflyfish", "Air Laut", R.drawable.ikan_butterflyfish));
        }   else if (categoryName.equals("Air Tawar")) {
            fishTypes.add(new FishType(12, "Guppy", "Air Tawar", R.drawable.ikan_guppy));
            fishTypes.add(new FishType(13, "Molly", "Air Tawar", R.drawable.ikan_molly));
            // Tambahkan lebih banyak tanaman jika diperlukan
        }   else if (categoryName.equals("Gabus")) {
            fishTypes.add(new FishType(14, "Gabus Channa", "Air Tawar", R.drawable.gabus_chana));
            fishTypes.add(new FishType(15, "Gabus Striata", "Air Tawar", R.drawable.gabus_striata));
            // Tambahkan lebih banyak tanaman jika diperlukan
        }

        return fishTypes;
    }
}
