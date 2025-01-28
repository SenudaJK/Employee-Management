package com.organization.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = "^[a-zA-Z']+$", message = "First name should only contain letters and ' symbol")
    @Size(max = 10, message = "First name should not exceed 10 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = "^[a-zA-Z']+$", message = "Last name should only contain letters and ' symbol")
    @Size(max = 20, message = "Last name should not exceed 20 characters")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email should not exceed 100 characters")
    private String email;

    @NotBlank(message = "Telephone number is mandatory")
    @Pattern(regexp = "^07\\d{8}$", message = "Telephone number should contain 10 digits and start with 07")
    @Size(max = 10, message = "Telephone number should not exceed 10 characters")
    private String telephone;
}