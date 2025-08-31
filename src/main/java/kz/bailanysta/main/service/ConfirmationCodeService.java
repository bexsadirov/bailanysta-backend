package kz.bailanysta.main.service;


import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.module.auth.EmailConfirmBody;

/**
 * @author Beksultan
 */
public interface ConfirmationCodeService {


    void sendEmail(EmailConfirmBody body) throws InputException;
    void deleteExpired();

    void validateCode(String phone, int code) throws InputException;

}
