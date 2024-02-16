package com.example.demo.controllers;

import com.example.demo.models.ApiResponse;
import com.example.demo.models.BookLockerRequest;
import com.example.demo.models.OpenLockerRequestBody;
import com.example.demo.models.entities.Locker;
import com.example.demo.services.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locker")
public class LockerController {

    @Autowired
    private LockerService lockerService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<List<Locker>>> allLocker() {
        List<Locker> result = lockerService.getAllLocker();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Get All Data", result));
    }

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<Object>> bookLocker(@RequestBody BookLockerRequest request) {
        String result = lockerService.bookLocker(request.getPhoneNumber(), request.getNumLockers());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, result));
    }

    @PostMapping("/return")
    public ResponseEntity<ApiResponse<Object>> returnLocker(@RequestParam String lockerNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, lockerService.returnLocker(lockerNumber)));
    }

    @PostMapping("/open")
    public ResponseEntity<ApiResponse<Object>> openLocker(@RequestBody OpenLockerRequestBody body) {
        String fopenLocker = lockerService.openLocker(body.getLockerNumber(), body.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, fopenLocker));
    }

    @PostMapping("/free-locker/{lockerNumber}")
    public ResponseEntity<ApiResponse<Object>> freeLocker(@PathVariable String lockerNumber) {
        String result = lockerService.freeLocker(lockerNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, result));
    }
}
