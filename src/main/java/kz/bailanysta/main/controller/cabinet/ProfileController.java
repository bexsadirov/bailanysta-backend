package kz.bailanysta.main.controller.cabinet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.bailanysta.main.config.ApiConfig;
import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.module.user.User;
import kz.bailanysta.main.module.user.dto.ProfileDto;
import kz.bailanysta.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = ApiConfig.PRIVATE_API_PREFIX + "profile")
@Api(tags = {"Мой профиль"}, value = "Profile Controller")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    @ApiOperation(tags = {"Кабинет"}, value = "Мои посты")
    public ProfileDto getMyProfile() throws InputException {
        User user = userService.findCurrentUser();
        return new ProfileDto(user);
    }
}
