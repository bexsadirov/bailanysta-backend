package kz.bailanysta.main.controller;

import kz.bailanysta.main.config.AppConfig;
import kz.bailanysta.main.module.auth.JWKs;
import kz.bailanysta.main.module.auth.RSAUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Beksultan
 */
@ApiIgnore
@RestController
@RequestMapping(value = ".well-known", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class WellKnownController {

    private final RSAUtil rsaUtil;

    @GetMapping("openid-configuration")
    public OpenIdConfiguration openIdConfiguration() { return new OpenIdConfiguration(AppConfig.ISSUER_URI); }

    @GetMapping(value = "keys", consumes = MediaType.ALL_VALUE)
    public JWKs getPublicKeys() { return new JWKs(rsaUtil.getPublicKeys()); }

    /*
     * Inner class
     */
    @Data
    private static class OpenIdConfiguration {

        /*
         * Fields
         */
        private String issuer;
        private String jwks_uri;

        public OpenIdConfiguration(String issuer) {
            this.issuer = issuer;
            this.jwks_uri = issuer + "/.well-known/keys";
        }
    }
}
