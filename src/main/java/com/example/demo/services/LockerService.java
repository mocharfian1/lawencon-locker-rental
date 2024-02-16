package com.example.demo.services;
import com.example.demo.models.entities.Locker;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.LockerRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
public class LockerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private JavaMailSender emailSender;

    private static final int DEPOSIT_PER_DAY = 10000;
    private static final int FINE_PER_DAY = 5000;
    private static final int MAX_RETRIES = 3;
    private static final int PASSWORD_USE_LIMIT = 2;
    private static final int RELEASE_FEE = 25000;

    public String bookLocker(String phoneNumber, int numLockers) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        List<Locker> lockerByPhone = lockerRepository.findByUserPhoneNumber(phoneNumber);

        if (user == null) {
            return "User not found";
        }

        if ((lockerByPhone.size() + numLockers) > 3) {
            return "The maximum lockers you can book is 3 lockers";
        }

        int totalDeposit = numLockers * DEPOSIT_PER_DAY;
        if (user.getDeposit() < totalDeposit) {
            return "Insufficient deposit";
        }

        for (int i = 0; i < numLockers; i++) {
            String lockerNumber = generateLockerNumber();
            String password = generatePassword();

            Locker locker = new Locker();
            locker.setLockerNumber(lockerNumber);
            locker.setPassword(password);
            locker.setBookingTime(System.currentTimeMillis());
            locker.setUserPhoneNumber(user.getPhoneNumber());

            lockerRepository.save(locker);

            sendBookingConfirmationEmail(user.getEmail(), lockerNumber, password);
        }

        user.setDeposit(user.getDeposit() - totalDeposit);
        userRepository.save(user);

        return "Locker(s) booked successfully. Username & Password are sent to email.";
    }

    public String returnLocker(String lockerNumber) {
        Locker locker = lockerRepository.findByLockerNumber(lockerNumber);
        if (locker == null) {
            return "Locker not found";
        }

        // Calculate duration of locker usage in days
        long durationInMillis = System.currentTimeMillis() - locker.getBookingTime();
        long durationInDays = TimeUnit.MILLISECONDS.toDays(durationInMillis) + 1;

        // Calculate total fine
        int totalFine = 0;
        if (durationInDays > 1) {
            totalFine = (int) ((durationInDays - 1) * FINE_PER_DAY);
        }

        // Calculate total deposit
        int totalDeposit = (int) (durationInDays * DEPOSIT_PER_DAY);

        // Calculate amount to return after deducting fine
        int amountToReturn = totalDeposit - totalFine;

        // Update user's deposit
        User user = userRepository.findByPhoneNumber(locker.getUserPhoneNumber());
        if (totalFine > user.getDeposit()) {
            // If fine exceeds deposit, user needs to pay release fee
            user.setDeposit(0);
            return "Fine exceeds deposit, please pay release fee";
        } else {
            user.setDeposit(user.getDeposit() + amountToReturn);
            userRepository.save(user);

            // Delete the locker record
            lockerRepository.delete(locker);

            return "Deposit returned: " + amountToReturn;
        }
    }

    public String openLocker(String lockerNumber, String password) {
        Locker locker = lockerRepository.findByLockerNumber(lockerNumber);
        if (locker == null) {
            return "Locker not found";
        }

        if (!locker.getPassword().equals(password)) {
            if (locker.getRetries() >= MAX_RETRIES) {
                return "Password attempts exceeded, locker freed";
            }
            locker.setRetries(locker.getRetries() + 1);
            lockerRepository.save(locker);
            return "Incorrect password";
        }

        if (locker.getUses() >= PASSWORD_USE_LIMIT - 1) {
            locker.setPassword(generatePassword());
        }
        locker.setUses(locker.getUses() + 1);
        lockerRepository.save(locker);

        return "Locker opened successfully";
    }

    public String freeLocker(String lockerNumber) {
        Locker locker = lockerRepository.findByLockerNumber(lockerNumber);
        if (locker == null) {
            return "Locker not found";
        }

        // Update user's deposit with release fee
        User user = userRepository.findByPhoneNumber(locker.getUserPhoneNumber());
        user.setDeposit(user.getDeposit() - RELEASE_FEE);
        userRepository.save(user);

        // Delete the locker record
        lockerRepository.delete(locker);

        return "Locker freed with release fee deducted";
    }

    private void sendBookingConfirmationEmail(String email, String numLockers, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Locker Booking Confirmation");
        message.setText("Dear Customer,\n\nYour booking for " + numLockers + " password: " + password + ".\n\nThank you.");

        emailSender.send(message);
    }

    private String generateLockerNumber() {
        // Logic to generate locker number
        return "L" + System.currentTimeMillis(); // Example logic
    }

    private String generatePassword() {
        // Logic to generate password
        return "P" + System.currentTimeMillis(); // Example logic
    }

    public List<Locker> getAllLocker() {
        return lockerRepository.findAll();
    }
}
