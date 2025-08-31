package kz.bailanysta.main.service;

import kz.bailanysta.main.module.auth.SignInBody;
import kz.bailanysta.main.module.auth.SignUpBody;
import kz.bailanysta.main.module.auth.Tokens;
import kz.bailanysta.main.module.user.User;

public interface AuthService {

    Tokens signIn(User user, SignInBody signInBody);
    Tokens signIn(User user, SignUpBody signUpBody);

}
