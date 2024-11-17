package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveUserRequest {

    @NotBlank(message = "Username is mandatory")
    @Size(max = 100, message = "Username must be less than 100 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(max = 100, message = "Password must be less than 100 characters")
    private String pwd;

    @NotNull(message = "System ID is mandatory")
    private Integer systemID;

    @NotNull(message = "Approval status is mandatory")
    private Integer approval;

    @NotNull(message = "Active status is mandatory")
    private Boolean active;

    @NotBlank(message = "Created by is mandatory")
    private String createdBy;

    private String lastModifiedBy;
}
