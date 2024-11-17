package com.example.demo.controller;

import com.example.demo.dto.LeaveUserRequest;
import com.example.demo.entity.ChangePasswordRequest;
import com.example.demo.service.LeaveUserService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final LeaveUserService leaveUserService;


    @PatchMapping("/reset-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/leave")
    public ResponseEntity<String> createLeaveUser(@RequestBody LeaveUserRequest request) {
        leaveUserService.createLeaveUser(request);
        return ResponseEntity.ok("User created successfully");
    }
}
