package kz.bailanysta.main.module.auth.confirmation_code;

import kz.bailanysta.main.exception.IncorrectCodeException;
import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.module.auth.EmailConfirmBody;
import kz.bailanysta.main.service.ConfirmationCodeService;
import kz.bailanysta.main.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Beksultan
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Throwable.class, dontRollbackOn = IncorrectCodeException.class)
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {

    private static final Long MINUTE = 60 * 1000L;

    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final EmailService emailService;

    @Override
    public void sendEmail(EmailConfirmBody body) throws InputException {
        int code = generateRandomCode();

        ConfirmationCode confirmationCode = findByDestination(body.email());
        if (confirmationCode != null) {
            validateSending(confirmationCode);
            confirmationCode.addNewCode(code);
        } else {
            confirmationCode = new ConfirmationCode();
            validateEmail(body.email());
            confirmationCode.setEmail(body.email());
            confirmationCode.setCodes(Collections.singletonList(code).toString());
            confirmationCode.setAttempts(0);
            confirmationCode.setCreateTime(new Date());
            confirmationCode.setType(ConfirmationCode.Type.EMAIL);
        }

        confirmationCode.setLastSentTime(LocalDateTime.now());
        confirmationCodeRepository.save(confirmationCode);

        String subject = "Код подтверждения";
        String messageBody = String.format("Ваш код подтверждения: %d", code);

        emailService.sendEmail(body.email(), subject, messageBody);
    }

    @Override
    public void deleteExpired() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(ConfirmationCode.EXPIRATION_MINUTES);
        confirmationCodeRepository.deleteExpired(expirationTime);
    }

    @Override
    public void validateCode(String email, int code) throws InputException {
        ConfirmationCode confirmationCode = findByDestination(email);

        // it might be null, if schedule (every 15 minutes) deleted the object before user entered the code
        if (confirmationCode == null) {
            throw new IncorrectCodeException("confirmation_code.expired");
        }

        List<Integer> codes = confirmationCode.getCodesList();
        boolean isValid = codes.contains(code);
        if (confirmationCode.getAttempts() >= ConfirmationCode.MAX_ATTEMPTS_COUNT
            || (confirmationCode.getAttempts() >= ConfirmationCode.MAX_ATTEMPTS_COUNT - 1 && !isValid)) {
            long diff = ChronoUnit.MILLIS.between(confirmationCode.getLastSentTime(), LocalDateTime.now());
            confirmationCode.setAttempts(ConfirmationCode.MAX_ATTEMPTS_COUNT);
            confirmationCodeRepository.save(confirmationCode);
            throw new IncorrectCodeException("confirmation_code.retry_code_entering", formatRetryWaitMinutes(diff));
        }

        // check correctness of code by one of the previously sent codes;
        // because users usually request two codes and enter the first received code
        if (!isValid) {
            int leftAttempts = ConfirmationCode.MAX_ATTEMPTS_COUNT - confirmationCode.getAttempts() - 1;
            confirmationCode.setAttempts(confirmationCode.getAttempts() + 1);
            confirmationCodeRepository.save(confirmationCode);
            throw new IncorrectCodeException("confirmation_code.incorrect", Integer.toString(leftAttempts));
        }
    }

    /*
     * Validation
     */
    private void validateSending(ConfirmationCode confirmationCode) throws InputException {
        long diff = ChronoUnit.MILLIS.between(confirmationCode.getLastSentTime(), LocalDateTime.now());

        if (confirmationCode.getCodesList().size() >= ConfirmationCode.MAX_ATTEMPTS_COUNT) {
            throw new InputException("confirmation_code.retry_sms_request", formatRetryWaitMinutes(diff));
        }

        if (confirmationCode.getAttempts() >= ConfirmationCode.MAX_ATTEMPTS_COUNT) {
            throw new InputException("confirmation_code.retry_code_entering", formatRetryWaitMinutes(diff));
        }

        if (diff < MINUTE) {
            String leftTime = Math.max(60 - (int) (diff / 1000), 0) + " сек.";
            throw new InputException("confirmation_code.wait_for_next_sms", leftTime);
        }
    }

    private void validateEmail(String email) throws InputException {
        if (email == null || email.isEmpty()) {
            throw new InputException("confirmation_code.invalid_email");
        }

        if (email.length() > ConfirmationCode.MAX_DESTINATION_LENGTH) {
            throw new InputException("confirmation_code.invalid_email_length");
        }
    }

    /*
     * Finders
     */
    private ConfirmationCode findByDestination(String email) {
        return confirmationCodeRepository.findByEmail(email);
    }

    /*
     * Helpers
     */
    private static int generateRandomCode() {
        return 7777;
    }

    private String formatRetryWaitMinutes(long timeDiffInMillis) {
        long leftTimeMillis = ConfirmationCode.EXPIRATION_MINUTES * MINUTE - timeDiffInMillis;
        if (leftTimeMillis <= MINUTE) {
            return Math.max(TimeUnit.MILLISECONDS.toSeconds(leftTimeMillis), 0) + " сек.";
        } else {
            return TimeUnit.MILLISECONDS.toMinutes(leftTimeMillis) + " мин.";
        }
    }
}
