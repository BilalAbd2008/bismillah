package com.example.bismillah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bismillah.models.UpdateRequest;
import com.example.bismillah.models.UpdateResponse;
import com.example.bismillah.network.ApiService;
import com.example.bismillah.network.RetrofitClient;
import com.example.bismillah.models.UserResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setting extends AppCompatActivity {
    private EditText edtName, edtUsername, edtEmail, edtPassword;
    private TextView tvDisplayUsername;
    private Button btnSave, btnDeleteAccount;
    private ImageButton btnBack, btnLogout;
    private ApiService apiService;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (!isLoggedIn()) {
            Toast.makeText(this, "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show();
            goToLogin();
            return;
        }
        
        initViews();
        initApiService();
        setupListeners();
        loadUserData();
    }

    private void initViews() {
        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tvDisplayUsername = findViewById(R.id.tvDisplayUsername);
        btnSave = findViewById(R.id.btnSave);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);
    }



    private void loadUserData() {
        String authToken = getToken();
        String userId = getUserId();

        if (userId == null || authToken == null) {
            Toast.makeText(this, "Terjadi kesalahan, silakan login kembali", Toast.LENGTH_SHORT).show();
            goToLogin();
            return;
        }

        Call<UserResponse> call = apiService.getUser(userId, "Bearer " + authToken);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if (userResponse.isSuccess() && userResponse.getData() != null) {
                        UserResponse.UserData userData = userResponse.getData();
                        tvDisplayUsername.setText(userData.getUsername());
                        edtName.setText(userData.getName());
                        edtUsername.setText(userData.getUsername());
                        edtEmail.setText(userData.getEmail());
                    } else {
                        Toast.makeText(Setting.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        goToLogin();
                    }
                } else {
                    if (response.code() == 401) {
                        Toast.makeText(Setting.this, "Sesi telah berakhir", Toast.LENGTH_SHORT).show();
                        goToLogin();
                    } else {
                        Toast.makeText(Setting.this,  response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(Setting.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        String name = edtName.getText().toString().trim();
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String authToken = getToken();
        String userId = getUserId();
    
        android.util.Log.d("UpdateProfile", "Attempting update with:");
        android.util.Log.d("UpdateProfile", "Token: " + authToken);
        android.util.Log.d("UpdateProfile", "UserId: " + userId);
        
        if (authToken == null || userId == null) {
            Toast.makeText(this, "Terjadi kesalahan autentikasi", Toast.LENGTH_SHORT).show();
            goToLogin();
            return;
        }
    
        if (name.isEmpty() || username.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Mohon lengkapi data", Toast.LENGTH_SHORT).show();
            return;
        }
    

        UpdateRequest request = new UpdateRequest(name, username, email, password);
        Call<UpdateResponse> call = apiService.updateUser(userId, request, "Bearer " + authToken);
        
        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {

                android.util.Log.d("UpdateProfile", "Response Code: " + response.code());
                
                if (response.isSuccessful() && response.body() != null) {
                    UpdateResponse updateResponse = response.body();
                    if (updateResponse.isSuccess()) {
                        Toast.makeText(Setting.this, updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        loadUserData(); // Refresh data
                        edtPassword.setText(""); // Clear password field
                    } else {
                        Toast.makeText(Setting.this,
                            updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? 
                            response.errorBody().string() : "Unknown error";
                        android.util.Log.e("UpdateProfile", "Error Body: " + errorBody);
                        Toast.makeText(Setting.this,
                            errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(Setting.this,
                            response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
    
            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {

                android.util.Log.e("UpdateProfile", "Network Error", t);
                Toast.makeText(Setting.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnLogout.setOnClickListener(v -> goToLogin());
        btnSave.setOnClickListener(v -> updateProfile());
        btnDeleteAccount.setOnClickListener(v -> showDeleteConfirmation());
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Akun")
                .setMessage("Anda yakin ingin menghapus akun?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    // Tampilkan loading dialog
                    
                            
                    deleteAccount();
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void deleteAccount() {
        String authToken = getToken();
        String userId = getUserId();
        
        android.util.Log.d("DeleteAccount", "Attempting delete with:");
        android.util.Log.d("DeleteAccount", "Token: " + authToken);
        android.util.Log.d("DeleteAccount", "UserId: " + userId);
        
        if (authToken == null || userId == null) {
            Toast.makeText(this, "Terjadi kesalahan autentikasi", Toast.LENGTH_SHORT).show();
            goToLogin();
            return;
        }
    
        Call<Void> call = apiService.deleteUser(userId, "Bearer " + authToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                android.util.Log.d("DeleteAccount", "Response Code: " + response.code());
                
                if (response.isSuccessful()) {
                    android.util.Log.d("DeleteAccount", "Delete successful");
                    clearUserData(); // Sekarang method ini sudah tersedia
                    Toast.makeText(Setting.this, "Akun berhasil dihapus", Toast.LENGTH_SHORT).show();
                    goToLogin();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        android.util.Log.e("DeleteAccount", "Error Body: " + errorBody);
                        Toast.makeText(Setting.this, "Gagal menghapus akun: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        android.util.Log.e("DeleteAccount", "Error reading error body", e);
                        Toast.makeText(Setting.this, "Gagal menghapus akun: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
    
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                android.util.Log.e("DeleteAccount", "Network Error", t);
                Toast.makeText(Setting.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    

    private void goToLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initApiService() {
        String authToken = getToken();
        userId = getUserId();
        if (authToken == null || userId == null) {
            Toast.makeText(this, "Sesi telah berakhir, silakan login kembali", Toast.LENGTH_SHORT).show();
            goToLogin();
            return;
        }
        apiService = RetrofitClient.getApiService(authToken);
    }



    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("authToken", null);
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("userId", null); // Pastikan key "userId" sama dengan yang digunakan saat login
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("authToken", null);
        String userId = sharedPreferences.getString("userId", null);
        return token != null && userId != null;
    }

    private void clearUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
