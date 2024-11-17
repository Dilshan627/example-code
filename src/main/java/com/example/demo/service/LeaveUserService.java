package com.example.demo.service;

import com.example.demo.dto.LeaveUserRequest;
import com.example.demo.entity.LeaveUser;
import com.example.demo.repository.LeaveUserRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveUserService {

    private final LeaveUserRepository leaveUserRepository;

    public LeaveUserService(LeaveUserRepository leaveUserRepository) {
        this.leaveUserRepository = leaveUserRepository;
    }

    public void createLeaveUser(LeaveUserRequest request) {
        LeaveUser leaveUser = LeaveUser.builder()
                .username(request.getUsername())
                .pwd(request.getPwd())
                .systemID(request.getSystemID())
                .approval(request.getApproval())
                .active(request.getActive())
                .createdBy(request.getCreatedBy())
                .build();

        leaveUserRepository.save(leaveUser);
    }

    public void updateLeaveUser(Integer id, LeaveUserRequest request) {
        LeaveUser leaveUser = leaveUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        leaveUser.setUsername(request.getUsername());
        leaveUser.setPwd(request.getPwd());
        leaveUser.setSystemID(request.getSystemID());
        leaveUser.setApproval(request.getApproval());
        leaveUser.setActive(request.getActive());
        leaveUser.setLastModifiedBy(request.getLastModifiedBy());

        leaveUserRepository.save(leaveUser);
    }
}
