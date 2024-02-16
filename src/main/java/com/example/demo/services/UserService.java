package com.example.demo.services;

import com.example.demo.models.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(String phoneNumber, String ktp, String email) {
        // Check if user with the same phone number already exists
        User existingUser = userRepository.findByPhoneNumber(phoneNumber);

        if (existingUser != null) {
            return "User with this phone number already exists";
        }

        // Create a new user
        User newUser = new User();
        newUser.setPhoneNumber(phoneNumber);
        newUser.setKtp(ktp);
        newUser.setEmail(email);
        newUser.setDeposit(0); // Set initial deposit to 0, user needs to top up later

        // Save the new user
        userRepository.save(newUser);

        return "User registered successfully";
    }

    public String topUpDeposit(String phoneNumber, int amount) {
        // Temukan pengguna berdasarkan nomor telepon
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            return "User not found";
        }

        // Tambahkan jumlah deposit
        user.setDeposit(user.getDeposit() + amount);

        // Simpan pengguna yang diperbarui
        userRepository.save(user);

        return "Deposit topped up successfully";
    }

    public String deleteUser(String phoneNumber) {
        // Find the user by phone number
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            return "User not found";
        }

        // Delete the user
        userRepository.delete(user);

        return "User deleted successfully";
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
