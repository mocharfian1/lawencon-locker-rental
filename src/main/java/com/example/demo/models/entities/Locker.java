package com.example.demo.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "locker")
public class Locker {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "locker_number", nullable = false)
    String lockerNumber;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "user_phone_number", nullable = false)
    String userPhoneNumber;

    @Column(name = "booking_time", nullable = false)
    long bookingTime;

    @Column(name = "uses", nullable = false)
    int uses;

    @Column(name = "retries", nullable = false)
    int retries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLockerNumber() {
        return lockerNumber;
    }

    public void setLockerNumber(String lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public long getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(long bookingTime) {
        this.bookingTime = bookingTime;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }
}
