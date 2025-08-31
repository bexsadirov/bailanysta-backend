package kz.bailanysta.main.module.user;

import org.springframework.security.core.context.SecurityContextHolder;

public class CurrUser {

    public static Integer getId() {
        try {
            return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (Exception e) {
            return null;
        }
    }

}
