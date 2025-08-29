package kz.bailanysta.main.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Beksultan
 */
@Configuration
public class AppConfig {

    public static String KEYS_PATH;
    public static String ISSUER_URI;

    @Value("${app.keys-path}")
    public void setKeysPath(String keysPath) { KEYS_PATH = keysPath; }

    @Value("${app.issuer-uri}")
    public void setIssuerUri(String issuerUri) { ISSUER_URI = issuerUri; }
}
