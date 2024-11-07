package com.example.bismillah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager; 
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FishPediaFragment extends Fragment {

    private RecyclerView airTawarRecyclerView;
    private RecyclerView airLautRecyclerView;
    private RecyclerView koiRecyclerView;
    private RecyclerView cupangRecyclerView;
    private RecyclerView gabusRecyclerView;
    
    private FishTypeAdapter airTawarAdapter;
    private FishTypeAdapter airLautAdapter;
    private FishTypeAdapter koiAdapter;
    private FishTypeAdapter cupangAdapter;
    private FishTypeAdapter gabusAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fishpedia, container, false);

       // Initialize RecyclerViews
       airTawarRecyclerView = view.findViewById(R.id.airTawarRecyclerView);
       airLautRecyclerView = view.findViewById(R.id.airLautRecyclerView);
       koiRecyclerView = view.findViewById(R.id.koiRecyclerView);
       cupangRecyclerView = view.findViewById(R.id.cupangRecyclerView);
       gabusRecyclerView = view.findViewById(R.id.gabusRecyclerView);

       // Set horizontal layout managers
       setupHorizontalRecyclerView(airTawarRecyclerView);
       setupHorizontalRecyclerView(airLautRecyclerView);
       setupHorizontalRecyclerView(koiRecyclerView);
       setupHorizontalRecyclerView(cupangRecyclerView);
       setupHorizontalRecyclerView(gabusRecyclerView);


       // Initialize data dan set adapters
       setupAirTawarSection();
       setupAirLautSection();
       setupKoiSection();
       setupCupangSection();
       setupGabusSection();

        // Initialize categories
        setUpCategoryClickListeners(view);

        return view;
    }

    private void setupHorizontalRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), 
            LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        
        // Tambahkan item decoration untuk spacing antar item
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerView.addItemDecoration(new ItemSpacingDecoration(spacingInPixels));
        
        // Nonaktifkan nested scrolling untuk smooth scrolling
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setupAirTawarSection() {
        List<FishType> airTawarFish = new ArrayList<>();
        airTawarFish.add(new FishType(1, "Guppy", "Air Tawar", R.drawable.ikan_guppy));
        airTawarFish.add(new FishType(2, "Molly", "Air Tawar", R.drawable.ikan_molly));
        airTawarFish.add(new FishType(3, "Guppy", "Air Tawar", R.drawable.ikan_guppy));
        airTawarFish.add(new FishType(4, "Molly", "Air Tawar", R.drawable.ikan_molly));
        airTawarAdapter = new FishTypeAdapter(airTawarFish, getContext());
        airTawarRecyclerView.setAdapter(airTawarAdapter);
    }

    private void setupAirLautSection() {
        List<FishType> airLautFish = new ArrayList<>();
        airLautFish.add(new FishType(5, "Nemo", "Air Laut", R.drawable.ikan_clownfish));
        airLautFish.add(new FishType(6, "Blue Tang", "Air Laut", R.drawable.ikan_lion_fish));
        airLautAdapter = new FishTypeAdapter(airLautFish, getContext());
        airLautRecyclerView.setAdapter(airLautAdapter);
    }

    private void setupKoiSection() {
        List<FishType> koiFish = new ArrayList<>();
        koiFish.add(new FishType(7, "Koi Tancho", "Air Tawar", R.drawable.koi_tancho));
        koiFish.add(new FishType(8, "Koi Chagoi", "Air Tawar", R.drawable.koi_chagoi));
        koiAdapter = new FishTypeAdapter(koiFish, getContext());
        koiRecyclerView.setAdapter(koiAdapter);
    }

    private void setupCupangSection() {
        List<FishType> cupangFish = new ArrayList<>();
        cupangFish.add(new FishType(9, "Cupang Halfmoon", "Air Tawar", R.drawable.cupang_halfmoon));
        cupangFish.add(new FishType(10, "Cupang Crown", "Air Tawar", R.drawable.cupang_betta));
        cupangAdapter = new FishTypeAdapter(cupangFish, getContext());
        cupangRecyclerView.setAdapter(cupangAdapter);
    }

    private void setupGabusSection() {
        List<FishType> gabusFish = new ArrayList<>();
        gabusFish.add(new FishType(11, "Gabus Channa", "Air Tawar", R.drawable.gabus_chana));
        gabusFish.add(new FishType(12, "Gabus Striata", "Air Tawar", R.drawable.gabus_striata));
        gabusAdapter = new FishTypeAdapter(gabusFish, getContext());
        gabusRecyclerView.setAdapter(gabusAdapter);
    }

    private void setUpCategoryClickListeners(View view) {
        LinearLayout categoryAirTawar = view.findViewById(R.id.categoryAirTawar);
        LinearLayout categoryAirLaut = view.findViewById(R.id.categoryAirLaut);
        LinearLayout categoryKoi = view.findViewById(R.id.categoryKoi);
        LinearLayout categoryCupang = view.findViewById(R.id.categoryCupang);
        LinearLayout categorygabus = view.findViewById(R.id.categoryGabus);

        categoryAirTawar.setOnClickListener(v -> openCategoryActivity("Air Tawar"));
        categoryAirLaut.setOnClickListener(v -> openCategoryActivity("Air Laut"));
        categoryKoi.setOnClickListener(v -> openCategoryActivity("Koi"));
        categoryCupang.setOnClickListener(v -> openCategoryActivity("Cupang"));
        categorygabus.setOnClickListener(v -> openCategoryActivity("Gabus"));
    }

    private void openCategoryActivity(String category) {
        Intent intent = new Intent(getActivity(), FishCategoryActivity.class);
        intent.putExtra("categoryName", category);
        startActivity(intent);
    }
}
