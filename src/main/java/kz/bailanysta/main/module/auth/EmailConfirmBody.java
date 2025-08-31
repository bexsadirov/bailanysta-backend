package kz.bailanysta.main.module.auth;

import lombok.Data;

/**
 * @author Beksultan
 */
public record EmailConfirmBody(String email, Purpose purpose) {

    public enum Purpose {
        SIGN_IN, SIGN_UP, FORGOT_PASSWORD
    }
}
