package kz.bailanysta.main.module.auth;

public record SignUpBody(String email, String username,
                         String name, String surname,
                         String password) {
}
