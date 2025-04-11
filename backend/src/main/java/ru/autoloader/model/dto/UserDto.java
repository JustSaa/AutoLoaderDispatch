package ru.autoloader.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.autoloader.model.UserRole;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private UserRole role;
}
