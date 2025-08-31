package kz.bailanysta.main.module.auth;

import kz.bailanysta.main.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

//    private final JavaMailSender javaMailSender;

//    @Value("${spring.mail.username}")
//    private String sender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//            helper.setFrom("Bailanysta <" + sender + ">");
//            helper.setText(body, true);
//            helper.setTo(to);
//            helper.setSubject(subject);
//
//            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }
}
