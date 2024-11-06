package com.example.bismillah;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bismillah.models.RegisterRequest;
import com.example.bismillah.models.RegisterResponse;
import com.example.bismillah.network.ApiService;
import com.example.bismillah.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText emailInput, passwordInput, nameInput, usernameInput;
    Button registerButton;
    TextView loginRedirect;
    ImageButton passtogel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.register_button);
        loginRedirect = findViewById(R.id.register_text);
        passtogel = findViewById(R.id.password_toggle);
        nameInput = findViewById(R.id.name_input);
        usernameInput = findViewById(R.id.username_input);

        loginRedirect.setOnClickListener(v -> goToLogin());
        registerButton.setOnClickListener(v -> registerUser());
        passtogel.setOnClickListener(v -> togglePasswordVisibility());
    }

    private void togglePasswordVisibility() {
        if (passwordInput.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passwordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
            passtogel.setImageResource(R.drawable.passtogel); // Mata terbuka
        } else {
            passwordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passtogel.setImageResource(R.drawable.matatutup); // Mata tertutup
        }
        passwordInput.setSelection(passwordInput.length());
    }

    private void registerUser() {
        String name = nameInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Register.this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isNetworkAvailable()) {
            Toast.makeText(Register.this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat objek RegisterRequest dengan username
        RegisterRequest registerRequest = new RegisterRequest(name, email, username, password);

        // Menggunakan Retrofit untuk melakukan permintaan pendaftaran
        ApiService apiService = RetrofitClient.getApiService();
        Call<RegisterResponse> call = apiService.register(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(Register.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    if (registerResponse.isSuccess()) {
                        // Pendaftaran berhasil, lanjut ke login
                        goToLogin();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(Register.this, "Pendaftaran gagal: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(Register.this, "Pendaftaran gagal, silakan coba lagi.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Register.this, "Gagal terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void goToLogin() {
        Intent intent = new Intent(Register.this, login.class);
        startActivity(intent);
        finish();
    }
}
