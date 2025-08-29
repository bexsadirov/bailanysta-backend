package kz.bailanysta.main.module.auth;

import java.util.List;
import lombok.Data;

/**
 * @author Beksultan
 */
@Data
public class JWKs {

    private String alg = "RSA";
    private List<PublicKey> keys;

    public JWKs(List<PublicKey> publicKeys) {
        keys = publicKeys;
    }

    @Data
    public static class PublicKey {
        private String kty;
        private String e;
        private String use;
        private String kid;
        private String alg;
        private String n;
    }
}
