package kz.bailanysta.main.module.user.password;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user__password")
@Accessors(chain = true)
public class UserPassword {

    public static final Integer MIN_LENGTH = 6;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "password")
    private String password;
}
