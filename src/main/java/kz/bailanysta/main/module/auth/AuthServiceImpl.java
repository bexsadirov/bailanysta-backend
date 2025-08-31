package kz.bailanysta.main.module.auth;

import kz.bailanysta.main.module.user.User;
import kz.bailanysta.main.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    @Override
    public Tokens signIn(User user, SignInBody signInBody) {
        return null;
    }

    @Override
    public Tokens signIn(User user, SignUpBody signUpBody) {
        return null;
    }

}
