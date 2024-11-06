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
import androidx.recyclerview.widget.RecyclerView;

public class FishPediaFragment extends Fragment {

    private RecyclerView popularRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fishpedia, container, false);

        // Set up the popular RecyclerView
        // popularRecyclerView = view.findViewById(R.id.popularRecyclerView);

        // Initialize categories
        setUpCategoryClickListeners(view);

        return view;
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
