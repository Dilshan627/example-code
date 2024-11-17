package com.example.demo.repository;

import com.example.demo.entity.LeaveUser;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveUserRepository extends JpaRepository<LeaveUser, Integer> {
}
