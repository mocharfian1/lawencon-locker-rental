package com.example.demo.models;

public class BookLockerRequest {
    private String phoneNumber;
    private int numLockers;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNumLockers() {
        return numLockers;
    }

    public void setNumLockers(int numLockers) {
        this.numLockers = numLockers;
    }
}