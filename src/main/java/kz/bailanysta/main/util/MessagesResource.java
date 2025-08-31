package kz.bailanysta.main.util;

import kz.bailanysta.main.exception.ForbiddenException;
import kz.bailanysta.main.exception.InputException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessagesResource {

    public static final String BUNDLE_BASE_NAME = "message";

    public static String message(Locale locale, String messageKey, Object... args) {
        if (messageKey == null) {
            return null;
        }

        ResourceBundle messageSource;
        try {
            messageSource = getMessagesBundle(locale);
        } catch (MissingResourceException e) {
            messageSource = getMessagesBundle(new Locale("ru"));
        }

        if (messageSource == null || !messageSource.containsKey(messageKey)) {
            return messageKey;
        }

        return MessageFormat.format(messageSource.getString(messageKey), args);
    }

    public static String message(InputException e) {
        Locale locale = LocaleContextHolder.getLocale();
        return message(locale, e.getMessageKey(), e.getArgs());
    }

    public static String message(ForbiddenException e) {
        Locale locale = LocaleContextHolder.getLocale();
        return message(locale, e.getMessageKey(), e.getArgs());
    }

    public static String message(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return message(locale, messageKey, args);
    }

    public static ResourceBundle getMessagesBundle(Locale locale) throws MissingResourceException {
        return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }
}
