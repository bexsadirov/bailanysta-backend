package kz.bailanysta.main.module.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nimbusds.jose.jwk.RSAKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import kz.bailanysta.main.config.AppConfig;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @author Beksultan
 */
@Getter
@Component
public class RSAUtil {

    /*
     * Constants
     */
    private static final String PUBLIC_KEYS_FILENAME = "auth-public-keys.json";
    private static final String PRIVATE_KEY_FILENAME = "auth-private-key.json";

    /*
     * Fields
     */
    private final List<JWKs.PublicKey> publicKeys;
    private final List<RSAKey> rsaPublicKeys;
    private final RSAKey rsaKey;

    public RSAUtil() {
        String publicKeyJson = readFromFile(AppConfig.KEYS_PATH, PUBLIC_KEYS_FILENAME);
        publicKeys = new Gson().fromJson(publicKeyJson, new TypeToken<List<JWKs.PublicKey>>() { }.getType());

        try {
            // parse rsa public keys
            rsaPublicKeys = new ArrayList<>();
            for (JWKs.PublicKey jwk : publicKeys) {
                rsaPublicKeys.add(RSAKey.parse(new Gson().toJson(jwk)));
            }

            rsaKey = RSAKey.parse(readFromFile(AppConfig.KEYS_PATH, PRIVATE_KEY_FILENAME));
        } catch (ParseException e) {
            throw new RuntimeException("Couldn't parse RSA key");
        }
    }

    /*
     * Helpers
     */
    private String readFromFile(String path, String fileName) {
        try {
            return Files.readString(Paths.get(path + fileName));
        } catch (Exception e) {
            return null;
        }
    }
}
