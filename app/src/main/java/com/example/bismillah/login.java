package com.example.bismillah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bismillah.models.LoginRequest;
import com.example.bismillah.models.LoginResponse;
import com.example.bismillah.network.ApiService;
import com.example.bismillah.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginButton;
    ImageButton passwordToggle;
    TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        registerText = findViewById(R.id.register_text);
        passwordToggle = findViewById(R.id.password_toggle);

        loginButton.setOnClickListener(v -> login());
        registerText.setOnClickListener(v -> goToRegister());
        passwordToggle.setOnClickListener(v -> togglePasswordVisibility());
    }

    private void togglePasswordVisibility() {
        if (passwordInput.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passwordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
            passwordToggle.setImageResource(R.drawable.passtogel);
        } else {
            passwordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordToggle.setImageResource(R.drawable.matatutup);
        }
        passwordInput.setSelection(passwordInput.length());
    }

    private void login() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(login.this, "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isNetworkAvailable()) {
            Toast.makeText(login.this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        LoginRequest loginRequest = new LoginRequest(email, password);

        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    
                    if (loginResponse.isSuccess()) {
                        saveToken(loginResponse.getToken());
                        saveUserId(loginResponse.getUser().getId());
                        saveUsername(loginResponse.getUser().getUsername());
                        saveName(loginResponse.getUser().getName());
                        saveEmail(loginResponse.getUser().getEmail());
                        
                        Toast.makeText(login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intentlogin = new Intent(login.this, Homepage.class);
                        intentlogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentlogin);
                        finish();
                    } else {
                        Toast.makeText(login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(login.this, "Login gagal, silakan coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(login.this, "Gagal terhubung ke server: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("authToken", token);
        editor.apply();
    }


    private void goToRegister() {
        Intent intent = new Intent(login.this, Register.class);
        startActivity(intent);
    }

    private void saveUserId(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }
    private void saveUsername(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    private void saveName(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    private void saveEmail(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }


}
