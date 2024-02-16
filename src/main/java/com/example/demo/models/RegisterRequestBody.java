package com.example.demo.models;

public class RegisterRequestBody {
    private String phoneNumber;
    private String ktp;
    private String email;

    public RegisterRequestBody(String phoneNumber, String ktp, String email) {
        this.phoneNumber = phoneNumber;
        this.ktp = ktp;
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
