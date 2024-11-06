// File: PelatihanFragment.java
package com.example.bismillah;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager; // Import ViewPager
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;
import java.util.ArrayList;

public class PelatihanFragment extends Fragment {

    private ViewPager viewPagerTopViewed; // Ganti ke ViewPager
    private ViewPager viewPagerOther;      // Ganti ke ViewPager
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pelatihan, container, false);

        // Inisialisasi ViewPager dan Toolbar
        viewPagerTopViewed = view.findViewById(R.id.viewPagerTopViewed);
        viewPagerOther = view.findViewById(R.id.viewPagerOther);
        toolbar = view.findViewById(R.id.toolbar);

        // Back button logic
        ImageButton backButton = toolbar.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        // Setup ViewPagers
        setupViewPagers();

        return view;
    }

    private void setupViewPagers() {
        // Contoh data dummy untuk Top Viewed
        List<Pelatihan> topViewedList = getTopViewedPelatihan();
        PelatihanAdapter topViewedAdapter = new PelatihanAdapter(getContext(), topViewedList, pelatihan -> {
            // Logic untuk navigasi ke DetailPelatihanFragment
            Fragment detailFragment = new DetailPelatihanFragment();
            Bundle args = new Bundle();
            args.putParcelable("pelatihan", pelatihan);
            detailFragment.setArguments(args);

            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        viewPagerTopViewed.setAdapter(topViewedAdapter);

        // Contoh data dummy untuk Other Pelatihan
        List<Pelatihan> otherPelatihanList = getOtherPelatihan();
        PelatihanAdapter otherPelatihanAdapter = new PelatihanAdapter(getContext(), otherPelatihanList, pelatihan -> {
            // Logic ketika klik item lainnya, misalnya membuka DetailActivity
            openDetailActivity(pelatihan);
        });
        viewPagerOther.setAdapter(otherPelatihanAdapter);
    }

    // Dummy data untuk Pelatihan
    private List<Pelatihan> getTopViewedPelatihan() {
        // Isi data untuk pelatihan yang paling populer
        List<Pelatihan> pelatihanList = new ArrayList<>();
        pelatihanList.add(new Pelatihan("Pelatihan Budidaya Ikan", R.drawable.tes1, 320000));
        pelatihanList.add(new Pelatihan("Pelatihan Aquascape", R.drawable.tes2, 450000));
        pelatihanList.add(new Pelatihan("Pelatihan Budidaya Ikan", R.drawable.tes1, 320000));
        pelatihanList.add(new Pelatihan("Pelatihan Aquascape", R.drawable.tes2, 450000));
        return pelatihanList;
    }

    private List<Pelatihan> getOtherPelatihan() {
        // Isi data untuk pelatihan lainnya
        List<Pelatihan> pelatihanList = new ArrayList<>();
        pelatihanList.add(new Pelatihan("Budidaya Ikan Hias", R.drawable.tes3, 300000));
        pelatihanList.add(new Pelatihan("Budidaya Ikan Air Laut", R.drawable.tes1, 350000));
        pelatihanList.add(new Pelatihan("Budidaya Ikan Hias", R.drawable.tes3, 300000));
        pelatihanList.add(new Pelatihan("Budidaya Ikan Air Laut", R.drawable.tes1, 350000));
        return pelatihanList;
    }

    // Metode untuk membuka DetailActivity
    private void openDetailActivity(Pelatihan pelatihan) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("TITLE", pelatihan.getTitle());
        intent.putExtra("PRICE", pelatihan.getPrice());
        intent.putExtra("IMAGE_RES_ID", pelatihan.getImageResId());
        startActivity(intent);
    }
}
