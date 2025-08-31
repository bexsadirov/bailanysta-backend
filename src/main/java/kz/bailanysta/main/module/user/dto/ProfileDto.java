package kz.bailanysta.main.module.user.dto;

import kz.bailanysta.main.module.user.User;

public record ProfileDto(Integer id, String name, String surname, String email, String username) {

    public ProfileDto(User user) {
        this(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getUsername());
    }
}
