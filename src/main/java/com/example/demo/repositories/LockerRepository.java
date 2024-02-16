package com.example.demo.repositories;

import com.example.demo.models.entities.Locker;
import com.example.demo.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
    Locker findByLockerNumber(String lockerNumber);
    List<Locker> findByUserPhoneNumber(String phoneNumber);
}
