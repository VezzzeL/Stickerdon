package com.stickerdon.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    @Size(min = 2, max = 12, message = "Invalid first name, 2-12 chars required")
    private String firstName;
    @Size(min = 2, max = 12, message = "Invalid last name, 2-12 chars required")
    private String lastName;

    private String username;
    @Size(min = 8, max = 16, message = "Invalid password, 8-16 chars required")
    private String password;

    private String repeatPassword;
}
