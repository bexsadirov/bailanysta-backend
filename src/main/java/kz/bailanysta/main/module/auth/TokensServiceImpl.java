package kz.bailanysta.main.module.auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import kz.bailanysta.main.config.AppConfig;
import kz.bailanysta.main.exception.UnauthorizedException;
import kz.bailanysta.main.service.TokensService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokensServiceImpl implements TokensService {

    public static final int ACCESS_TOKEN_EXPIRATION_SECONDS =  60 * 60;

    private final RSAUtil rsaUtil;

    @Override
    public Tokens generateTokens(Integer userId) {
        String accessToken = generateAccessToken(userId);
        String refreshToken = generateRefreshToken(userId);
        return new Tokens(accessToken, refreshToken, ACCESS_TOKEN_EXPIRATION_SECONDS);
    }

    private String generateAccessToken(Integer userId) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(AppConfig.ISSUER_URI)
                .issueTime(toDate(LocalDateTime.now()))
                .subject(userId.toString())
                .expirationTime(toDate(LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRATION_SECONDS)))
                .build();

            return generateToken(claimsSet);
        } catch (JOSEException e) {
            throw new RuntimeException("Access token creation failed");
        }
    }

    private String generateRefreshToken(Integer userId) {
        try {
            Date expirationTime = toDate(LocalDateTime.now().plusMonths(3));

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(AppConfig.ISSUER_URI)
                .issueTime(toDate(LocalDateTime.now()))
                .subject(userId.toString())
                .expirationTime(expirationTime)
                .build();

            return generateToken(claimsSet);
        } catch (JOSEException e) {
            throw new RuntimeException("Refresh token creation failed");
        }
    }

    /*
     * Validation
     */
    @Override
    public JWTClaimsSet validateAndParse(String token) throws UnauthorizedException {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier(rsaUtil.getRSAPublicKeyById(signedJWT.getHeader().getKeyID()));
            if (!signedJWT.verify(verifier)
                || signedJWT.getJWTClaimsSet().getExpirationTime().before(toDate(LocalDateTime.now()))) {
                throw new UnauthorizedException();
            }

            return signedJWT.getJWTClaimsSet();
        } catch (JOSEException | ParseException e) {
            throw new UnauthorizedException();
        }
    }

    private Date toDate(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    private String generateToken(JWTClaimsSet claimsSet) throws JOSEException {
        RSAKey rsaKey = rsaUtil.getRsaKey();
        JWSSigner signer = new RSASSASigner(rsaKey);

        SignedJWT signedJWT = new SignedJWT(
            new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
            claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
}
