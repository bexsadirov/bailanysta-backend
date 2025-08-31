package kz.bailanysta.main.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.bailanysta.main.config.ApiConfig;
import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.module.auth.EmailCodeBody;
import kz.bailanysta.main.module.auth.EmailConfirmBody;
import kz.bailanysta.main.module.user.User;
import kz.bailanysta.main.service.ConfirmationCodeService;
import kz.bailanysta.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Beksultan
 */
@Slf4j
@RestController
@RequestMapping(value = ApiConfig.AUTH_API_PREFIX + "email-verification")
@Api(tags = {"Авторизация"}, value = "EmailVerifyController")
@RequiredArgsConstructor
public class EmailVerifyController {

    private final UserService userService;
    private final ConfirmationCodeService confirmationCodeService;

    @PostMapping("request-code")
    @ApiOperation(tags = {"Авторизация"}, value = "Запрос кода для подтверждения почты")
    public void requestCode(@RequestBody EmailConfirmBody body) throws InputException {
        User oldUser = userService.findByEmail(body.email());
        validateUserExistence(body, oldUser);
        confirmationCodeService.sendEmail(body);
    }

    @PostMapping("validate-code")
    @ApiOperation(tags = {"Авторизация"}, value = "Проверка кода для подтверждения почты")
    public void validateCode(@RequestBody EmailCodeBody body) throws InputException {
        confirmationCodeService.validateCode(body.email(), body.code());
    }

    /*
     * Validation
     */
    private void validateUserExistence(EmailConfirmBody emailConfirmBody, User oldUser) throws InputException {
        switch (emailConfirmBody.purpose()) {
            case SIGN_IN -> {
                // it doesn't matter whether user exists or not
            }
            case SIGN_UP -> {
                if (oldUser != null) {
                    throw new InputException("user.already_registered");
                }
            }
            case FORGOT_PASSWORD -> {
                if (oldUser == null) {
                    throw new InputException("user.not_registered");
                }
            }
        }
    }

}
