package com.example.bismillah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class FishAdapter extends PagerAdapter {

    private Context context;
    private List<FishModel> fishList;

    // Constructor
    public FishAdapter(Context context, List<FishModel> fishList) {
        this.context = context;
        this.fishList = fishList;
    }

    @Override
    public int getCount() {
        // Return the number of items in the fish list
        return (int) Math.ceil(fishList.size() / 2.0); // Membagi list menjadi 2 fish per halaman
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // Check if a view corresponds to an object (required for PagerAdapter)
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate the layout for two CardView
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_fish_card, container, false);

        // Mengambil posisi data ikan pertama
        FishModel fish1 = fishList.get(position * 2);

        // Mengikat data ke CardView 1
        TextView fishName1 = view.findViewById(R.id.fishName1);
        TextView fishCategory1 = view.findViewById(R.id.fishCategory1);
        TextView badge1 = view.findViewById(R.id.badge1);
        ImageView fishImage1 = view.findViewById(R.id.fishImage1);

        fishName1.setText(fish1.getName());
        fishCategory1.setText(fish1.getCategory());
        badge1.setText(fish1.isTrending() ? "Sedang Tren" : "Populer");
        fishImage1.setImageResource(fish1.getImage());

        // Mengecek apakah ada ikan kedua (agar tidak crash jika jumlah ikan ganjil)
        if (position * 2 + 1 < fishList.size()) {
            FishModel fish2 = fishList.get(position * 2 + 1);

            // Mengikat data ke CardView 2
            TextView fishName2 = view.findViewById(R.id.fishName2);
            TextView fishCategory2 = view.findViewById(R.id.fishCategory2);
            TextView badge2 = view.findViewById(R.id.badge2);
            ImageView fishImage2 = view.findViewById(R.id.fishImage2);

            fishName2.setText(fish2.getName());
            fishCategory2.setText(fish2.getCategory());
            badge2.setText(fish2.isTrending() ? "Paling Laris" : "Populer");
            fishImage2.setImageResource(fish2.getImage());

        } else {
            // Sembunyikan CardView 2 jika tidak ada ikan kedua
            view.findViewById(R.id.fishCard2).setVisibility(View.INVISIBLE);
        }

        // Add the view to the container (ViewPager)
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // Remove the view from the container (ViewPager)
        container.removeView((View) object);
    }
}
