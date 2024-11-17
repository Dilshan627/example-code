package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "leave_user")
public class LeaveUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @Column(name = "pwd", nullable = false, length = 100)
    private String pwd;

    @Column(name = "systemID", nullable = false)
    private int systemID;

    @Column(name = "Approval", nullable = false, columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private int approval;

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean active;

    @CreatedDate
    @Column(name = "createDate", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "lastModified")
    private LocalDateTime lastModified;

    @Column(name = "createdBy", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "lastModifiedBy")
    private String lastModifiedBy;
}
