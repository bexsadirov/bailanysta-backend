package kz.bailanysta.main.service;

import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.module.auth.SignUpBody;
import kz.bailanysta.main.module.user.User;

public interface UserService {

    User create(SignUpBody signUpBody) throws InputException;
    void edit();

    User authenticate(String email, String password) throws InputException;


    /*
     * Finders
     */
    User findById(Integer id);
    User findByEmail(String email);
    User findByUsername(String username);
    User findCurrentUser();
}
