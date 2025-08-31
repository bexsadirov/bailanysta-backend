package kz.bailanysta.main.module.user;

import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.module.auth.SignUpBody;
import kz.bailanysta.main.module.user.password.UserPassword;
import kz.bailanysta.main.module.user.password.UserPasswordRepository;
import kz.bailanysta.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@Transactional(rollbackOn = Throwable.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;

    /*
     * Editors
     */
    @Override
    public User create(SignUpBody signUpBody) throws InputException {
        validate(signUpBody);

        User user = new User();
        user.setEmail(signUpBody.email());
        user.setUsername(signUpBody.username());
        user.setName(signUpBody.name());
        user.setSurname(signUpBody.surname());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        // todo encode the password
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(user.getId());
        userPassword.setPassword(signUpBody.password());
        userPasswordRepository.save(userPassword);

        return user;
    }

    @Override
    public void edit() {

    }

    @Override
    public User authenticate(String username, String password) throws InputException {
        User user = findByUsername(username);
        if (user == null) {
            throw new InputException("user.not_found");
        }

        UserPassword userPassword = userPasswordRepository.findByUserId(user.getId());
        if (!Objects.equals(userPassword.getPassword(), password)) {
            throw new InputException("user.invalid_password");
        }

        return user;
    }

    private void validate(SignUpBody signUpBody) throws InputException {
        if (userRepository.findByEmail(signUpBody.email()) != null) {
            throw new InputException("user.already_registered");
        }

        if (userRepository.findByUsername(signUpBody.username()) != null) {
            throw new InputException("user.already_registered");
        }
    }

    /*
     * Finders
     */
    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findCurrentUser() {
        return findById(CurrUser.getId());
    }
}
