package kz.bailanysta.main.module.auth;

import kz.bailanysta.main.module.user.User;
import kz.bailanysta.main.service.AuthService;
import kz.bailanysta.main.service.TokensService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final TokensService tokensService;

    @Override
    public Tokens signIn(User user) {
        return tokensService.generateTokens(user.getId());
    }
}
