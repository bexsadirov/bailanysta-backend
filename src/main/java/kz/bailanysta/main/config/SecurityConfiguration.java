package kz.bailanysta.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String[] SWAGGER_PATHS = {
        // -- Swagger UI v2
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        // -- Swagger UI v3
        "/v3/api-docs/**",
        "/swagger-ui/**",
    };


    @Override
    public void configure(WebSecurity web) {
        web
            .ignoring()
            .antMatchers("/" + ApiConfig.AUTH_API_PREFIX + "**")
            .antMatchers("/" + ApiConfig.PUBLIC_API_PREFIX + "**")
            .antMatchers("/api/.well-known/**")
            .antMatchers(SWAGGER_PATHS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and().authorizeRequests()
            .antMatchers("/" + ApiConfig.PRIVATE_API_PREFIX + "**").authenticated()
            .anyRequest().permitAll()
            .and().oauth2ResourceServer().jwt();
    }


}
