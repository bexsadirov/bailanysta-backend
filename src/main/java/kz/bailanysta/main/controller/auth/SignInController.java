package kz.bailanysta.main.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.bailanysta.main.config.ApiConfig;
import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.module.auth.SignInBody;
import kz.bailanysta.main.module.auth.Tokens;
import kz.bailanysta.main.module.user.User;
import kz.bailanysta.main.service.AuthService;
import kz.bailanysta.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Beksultan
 */
@Slf4j
@RestController
@RequestMapping(value = ApiConfig.AUTH_API_PREFIX + "sign-in")
@Api(tags = {"Авторизация"}, value = "Auth Controller")
@RequiredArgsConstructor
public class SignInController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    @ApiOperation(tags = {"Авторизация"}, value = "Авторизация пользователя")
    public Tokens signIn(@RequestBody SignInBody signInBody) throws InputException {
        User user = userService.authenticate(signInBody.username(), signInBody.password());
        return authService.signIn(user);
    }

}
