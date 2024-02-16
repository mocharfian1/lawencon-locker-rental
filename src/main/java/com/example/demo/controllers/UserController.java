package com.example.demo.controllers;

import com.example.demo.models.ApiResponse;
import com.example.demo.models.RegisterRequestBody;
import com.example.demo.models.TopupDepositRequestBody;
import com.example.demo.models.entities.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<List<User>>> allUser() {
        List<User> result = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Get All User", result));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> registerUser(@RequestBody RegisterRequestBody body) {
        String result = userService.registerUser(body.getPhoneNumber(), body.getKtp(), body.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, result));
    }

    @PostMapping("/top-up-deposit")
    public ResponseEntity<ApiResponse<Object>> topUpDeposit(@RequestBody TopupDepositRequestBody request) {
        String result = userService.topUpDeposit(request.getPhoneNumber(), request.getAmount());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, result));
    }
}