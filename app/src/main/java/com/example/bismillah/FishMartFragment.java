package com.example.bismillah;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class FishMartFragment extends Fragment implements FishMartAdapter.OnProductClickListener {

    private ViewPager viewPagerFilterAir;
    private ViewPager viewPagerPeletIkan;
    private ViewPager viewPagerTanamanHias;
    private ViewPager viewPagerBatuCoral;
    private ViewPager viewPagerAquascape;
    private ViewPager promoViewPager;

    private ImageButton cartButton;

    private Handler promoHandler = new Handler(); // Handler untuk auto-slide promo

    private int currentPage = 0; // Halaman saat ini di promo ViewPager

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fish_mart, container, false);

        // Inisialisasi ViewPagers
        viewPagerFilterAir = view.findViewById(R.id.viewPagerFilterAir);
        viewPagerPeletIkan = view.findViewById(R.id.viewPagerPeletIkan);
        viewPagerTanamanHias = view.findViewById(R.id.viewPagerTanamanHias);
        viewPagerBatuCoral = view.findViewById(R.id.viewPagerBatuCoral);
        viewPagerAquascape = view.findViewById(R.id.viewPagerAquascape);
        promoViewPager = view.findViewById(R.id.promoViewPager); // Promo ViewPager

        // Setup ViewPagers dengan adapter yang sesuai
        setupViewPager(viewPagerFilterAir, getItemsForCategory("Filter Air"));
        setupViewPager(viewPagerPeletIkan, getItemsForCategory("Pelet Ikan"));
        setupViewPager(viewPagerTanamanHias, getItemsForCategory("Tanaman Hias"));
        setupViewPager(viewPagerBatuCoral, getItemsForCategory("Batu Coral"));
        setupViewPager(viewPagerAquascape, getItemsForCategory("Aquascape"));

        // Setup promo ViewPager dengan auto-slide
        setupPromoViewPager();

        // Setup listeners untuk kategori untuk auto scroll ke ViewPager
        LinearLayout categoryFilterAir = view.findViewById(R.id.categoryFilterAir);
        LinearLayout categoryPeletIkan = view.findViewById(R.id.categoryPeletIkan);
        LinearLayout categoryTanamanHias = view.findViewById(R.id.categoryTanamanHias);
        LinearLayout categoryBatuCoral = view.findViewById(R.id.categoryBatuCoral);
        LinearLayout categoryAquascape = view.findViewById(R.id.categoryAquascape);

        // Listener untuk kategori
        categoryFilterAir.setOnClickListener(v -> scrollToViewPager(viewPagerFilterAir));
        categoryPeletIkan.setOnClickListener(v -> scrollToViewPager(viewPagerPeletIkan));
        categoryTanamanHias.setOnClickListener(v -> scrollToViewPager(viewPagerTanamanHias));
        categoryBatuCoral.setOnClickListener(v -> scrollToViewPager(viewPagerBatuCoral));
        categoryAquascape.setOnClickListener(v -> scrollToViewPager(viewPagerAquascape));

        // Cart button click listener
        cartButton = view.findViewById(R.id.cartButton); // Ambil referensi dari cartButton
        cartButton.setOnClickListener(v -> {
            // Tidak lagi mengarahkan ke CartActivity, hanya menampilkan toast
            Toast.makeText(getActivity(), "Keranjang terbuka", Toast.LENGTH_SHORT).show();
            // Jika Anda ingin mengarahkan ke CartActivity, bisa ditambahkan di sini
        });

        return view;
    }

    // Fungsi untuk scroll ke ViewPager yang sesuai
    private void scrollToViewPager(ViewPager viewPager) {
        // Mendapatkan ScrollView dari layout utama
        ScrollView scrollView = getActivity().findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, viewPager.getTop()); // Scroll ke ViewPager
    }

    // Setup ViewPager untuk kategori produk
    private void setupViewPager(ViewPager viewPager, List<FishMartItem> items) {
        FishMartAdapter adapter = new FishMartAdapter(items, getContext(), this); // Pass listener
        viewPager.setAdapter(adapter);
    }

    // Setup ViewPager untuk promo dengan auto-slide
    private void setupPromoViewPager() {
        // Dummy data untuk promo images
        List<Integer> promoImages = new ArrayList<>();
        promoImages.add(R.drawable.promo1);
        promoImages.add(R.drawable.promo2);
        promoImages.add(R.drawable.promo3);
        promoImages.add(R.drawable.promo3);
        promoImages.add(R.drawable.promo3);

        PromoAdapter promoAdapter = new PromoAdapter(promoImages, getContext());
        promoViewPager.setAdapter(promoAdapter);

        // Mulai auto-slide
        startPromoAutoSlide();
    }

    // Runnable untuk auto-slide promo ViewPager
    private Runnable promoRunnable = new Runnable() {
        @Override
        public void run() {
            if (promoViewPager.getAdapter() != null) {
                int totalPages = promoViewPager.getAdapter().getCount();
                currentPage = (currentPage + 1) % totalPages;
                promoViewPager.setCurrentItem(currentPage, true);
            }
            promoHandler.postDelayed(this, 4000); // Auto-slide setiap 4 detik
        }
    };

    // Memulai auto-slide promo
    private void startPromoAutoSlide() {
        promoHandler.postDelayed(promoRunnable, 4000); // Mulai auto-slide setelah 4 detik
    }

    // Hentikan auto-slide ketika fragment tidak aktif
    @Override
    public void onPause() {
        super.onPause();
        promoHandler.removeCallbacks(promoRunnable); // Hentikan auto-slide
    }

    // Lanjutkan auto-slide ketika fragment kembali aktif
    @Override
    public void onResume() {
        super.onResume();
        promoHandler.postDelayed(promoRunnable, 3000); // Lanjutkan auto-slide
    }

    // Dummy data untuk kategori produk
    private List<FishMartItem> getItemsForCategory(String category) {
        List<FishMartItem> itemList = new ArrayList<>();

        if (category.equals("Filter Air")) {
            itemList.add(new FishMartItem(R.drawable.filterair, "Filter Air 1", "Rp 100.000", "Deskripsi Filter Air 1"));
            itemList.add(new FishMartItem(R.drawable.filterair, "Filter Air 2", "Rp 150.000", "Deskripsi Filter Air 2"));
        } else if (category.equals("Pelet Ikan")) {
            itemList.add(new FishMartItem(R.drawable.peleikan, "Pelet Ikan 1", "Rp 10.000", "Deskripsi Pelet Ikan 1"));
            itemList.add(new FishMartItem(R.drawable.peleikan, "Pelet Ikan 2", "Rp 15.000", "Deskripsi Pelet Ikan 2"));
        } else if (category.equals("Tanaman Hias")) {
            itemList.add(new FishMartItem(R.drawable.tanaman_vallisneria, "Tanaman Hias 1", "Rp 50.000", "Tanaman ini memiliki daun panjang berbentuk pita yang dapat tumbuh hingga mencapai permukaan air. Daunnya hijau cerah dengan ujung meruncing. Vallisneria sering digunakan sebagai latar belakang di akuarium karena pertumbuhannya yang cepat dan mudah dirawat."));
            itemList.add(new FishMartItem(R.drawable.tanaman_vallisneria, "Tanaman Hias 2", "Rp 60.000", "Deskripsi Tanaman Hias 2"));
        } else if (category.equals("Batu Coral")) {
            itemList.add(new FishMartItem(R.drawable.batucoral, "Batu Coral 1", "Rp 30.000", "Deskripsi Batu Coral 1"));
            itemList.add(new FishMartItem(R.drawable.batucoral, "Batu Coral 2", "Rp 40.000", "Deskripsi Batu Coral 2"));
        } else if (category.equals("Aquascape")) {
            itemList.add(new FishMartItem(R.drawable.aquascape, "Aquascape 1", "Rp 70.000", "Deskripsi Aquascape 1"));
            itemList.add(new FishMartItem(R.drawable.aquascape, "Aquascape 2", "Rp 80.000", "Deskripsi Aquascape 2"));
        }

        return itemList;
    }

    // Metode untuk membuka ProductDetailActivity
    private void openDetailActivity(@NonNull FishMartItem product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("name", product.getName()); // Kirim nama produk
        intent.putExtra("price", product.getPrice()); // Kirim harga produk
        intent.putExtra("imageResourceId", product.getImageResId()); // Kirim ID gambar produk
        intent.putExtra("description", product.getDescription()); // Kirim deskripsi produk
        startActivity(intent); // Mulai ProductDetailActivity
    }

    // Implementasi OnProductClickListener
    @Override
    public void onProductClick(FishMartItem product) {
        openDetailActivity(product);
    }
}
