package kz.bailanysta.main.service;

import com.nimbusds.jwt.JWTClaimsSet;
import kz.bailanysta.main.exception.UnauthorizedException;
import kz.bailanysta.main.module.auth.Tokens;

/**
 * @author Beksultan
 */
public interface TokensService {

    Tokens generateTokens(Integer userId);

    JWTClaimsSet validateAndParse(String token) throws UnauthorizedException;
}
