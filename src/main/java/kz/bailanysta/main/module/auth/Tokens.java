package kz.bailanysta.main.module.auth;

import lombok.Data;

/**
 * @author Beksultan
 */
public record Tokens(String accessToken, String refreshToken, int expiresIn) {
}