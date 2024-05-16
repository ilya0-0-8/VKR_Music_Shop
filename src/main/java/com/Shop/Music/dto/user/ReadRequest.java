package com.Shop.Music.dto.user;

import com.Shop.Music.models.enums.Role;
import lombok.Data;

@Data
public class ReadRequest {
    private Long id;
    private String username;
    private String email;
    private String number;
    private String password;
    private boolean active;
    private Role role;
}
