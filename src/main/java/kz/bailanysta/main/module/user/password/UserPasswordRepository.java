package kz.bailanysta.main.module.user.password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPassword, Integer> {

    UserPassword findByUserId(Integer userId);
}
