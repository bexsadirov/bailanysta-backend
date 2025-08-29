package kz.bailanysta.main.module.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
