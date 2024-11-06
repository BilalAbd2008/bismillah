package com.example.bismillah.models;

public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private UserData user;

    public static class UserData {
        private String id;
        private String username;
        private String name;
        private String email;

        // Getters
        public String getId() { return id; }
        public String getUsername() { return username; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getToken() { return token; }
    public UserData getUser() { return user; }
}