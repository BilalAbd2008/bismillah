// File: HomeFragment.java
package com.example.bismillah;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.bismillah.models.UserResponse;
import com.example.bismillah.network.ApiService;
import com.example.bismillah.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView txtWelcome, viewAllTraining, viewAllFish;
    private ImageView imgProfile, imgNotification, btnGoToFishPedia;
    private ViewPager viewPagerTraining, viewPagerFish;
    private TrainingAdapter trainingAdapter;
    private FishAdapter fishAdapter;
    private ActionBar actionBar;
    private List<TrainingModel> trainingList;
    private List<FishModel> fishList;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize UI components
        txtWelcome = view.findViewById(R.id.txtWelcome);
        imgProfile = view.findViewById(R.id.imgProfile);
        imgNotification = view.findViewById(R.id.imgNotification);
        viewAllTraining = view.findViewById(R.id.viewAllTraining);
        viewAllFish = view.findViewById(R.id.viewAllFish);
        btnGoToFishPedia = view.findViewById(R.id.btnGoToFishPedia);
        viewPagerTraining = view.findViewById(R.id.viewPagerTraining);
        viewPagerFish = view.findViewById(R.id.viewPagerFish);

        // Set dynamic user name (e.g., from database)
        loadUsername();

        // Profile click event to show popup menu
        imgProfile.setOnClickListener(this::showProfilePopup);

        // Button to navigate to FishPediaFragment
        btnGoToFishPedia.setOnClickListener(v -> navigateToFishPedia());

        // Button to view all training
        viewAllTraining.setOnClickListener(v -> navigateToTraining());

        // Button to view all fish
        viewAllFish.setOnClickListener(v -> navigateToFishMart());

        // ActionBar setup
        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Home"); // Set initial ActionBar title if needed
        }

        // Load card data and setup ViewPagers
        loadCards();
        setupViewPagers();

        return view;
    }

    private void loadCards() {
        // Dummy data for training
        trainingList = new ArrayList<>();
        trainingList.add(new TrainingModel("Pelatihan Budidaya Ikan", 320000, R.drawable.tes1));
        trainingList.add(new TrainingModel("Pelatihan Aquascape", 450000, R.drawable.tes2));
        trainingList.add(new TrainingModel("Pelatihan Budidaya Ikan Lanjut", 500000, R.drawable.tes3));
        trainingList.add(new TrainingModel("Pelatihan Manajemen Ikan", 400000, R.drawable.tes3));

        // Dummy data for fish
        fishList = new ArrayList<>();
        fishList.add(new FishModel("Ikan Koi", R.drawable.koi, "Air Tawar", true));  // Tambahkan kategori dan trending
        fishList.add(new FishModel("Ikan Cupang", R.drawable.neontetra, "Air Tawar", false));
        fishList.add(new FishModel("Ikan Lele", R.drawable.koi, "Air Tawar", true));
        fishList.add(new FishModel("Ikan Guppy", R.drawable.neontetra, "Air Laut", false));
        // Tambahkan kategori dan trending
    }

    private void loadUsername() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);
        String token = sharedPreferences.getString("authToken", null);

        if (userId != null && token != null) {
            ApiService apiService = RetrofitClient.getApiService(token);
            Call<UserResponse> call = apiService.getUser(userId, "Bearer " + token);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (isAdded() && getActivity() != null) {  // Cek apakah Fragment masih attached
                        if (response.isSuccessful() && response.body() != null) {
                            UserResponse userResponse = response.body();
                            if (userResponse.isSuccess() && userResponse.getData() != null) {
                                String username = userResponse.getData().getUsername();
                                txtWelcome.setText("Hai, " + username);
                                // Debug
                                
                            } else {
                                txtWelcome.setText("Hai, Pengguna");
                                // Debug
                                Toast.makeText(getActivity(), "User data null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            txtWelcome.setText("Hai, Pengguna");
                            // Debug
                            Toast.makeText(getActivity(), "Response not successful: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    if (isAdded() && getActivity() != null) {
                        txtWelcome.setText("Hai, Pengguna");
                        // Debug
                        Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            txtWelcome.setText("Hai, Pengguna");
            // Debug
            Toast.makeText(requireActivity(), "Token atau UserId null", Toast.LENGTH_SHORT).show();
        }


    }

    private void setupViewPagers() {
        // Set adapter for training ViewPager
        trainingAdapter = new TrainingAdapter(getContext(), trainingList);
        viewPagerTraining.setAdapter(trainingAdapter);

        // Set adapter for fish ViewPager
        fishAdapter = new FishAdapter(getContext(), fishList);
        viewPagerFish.setAdapter(fishAdapter);

        // Set dynamic title when page changes
        viewPagerTraining.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (actionBar != null) {
                    int totalPages = trainingAdapter.getCount();
                    int trainingSize = trainingList.size();
                    int currentItem = position * 2;

                    if (currentItem < trainingSize) {
                        String title = trainingList.get(currentItem).getTitle();
                        actionBar.setTitle(title);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void showProfilePopup(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.profile_popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_settings) {
                navigateToSettings();
                return true;
            } else if (itemId == R.id.action_logout) {
                performLogout();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void navigateToFishPedia() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new FishPediaFragment())
                .addToBackStack(null)
                .commit();
    }

    private void navigateToTraining() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new PelatihanFragment())
                .addToBackStack(null)
                .commit();
    }

    private void navigateToFishMart() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new FishMartFragment())
                .addToBackStack(null)
                .commit();
    }

    private void navigateToSettings() {
        String token = getToken();
        String userId = getUserId();
        
        if (token == null || userId == null) {
            Toast.makeText(getActivity(), "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }
        
        Intent intent = new Intent(getActivity(), Setting.class);
        startActivity(intent);
    }

    private void performLogout() {
        // Hapus semua data user dari SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Kembali ke halaman login
        Intent intent = new Intent(getActivity(), login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }


    private SharedPreferences getSharedPreferences() {
        return requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }

    private String getToken() {
        return getSharedPreferences().getString("authToken", null);
    }

    private String getUserId() {
        return getSharedPreferences().getString("userId", null);
    }

    private void clearUserData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
