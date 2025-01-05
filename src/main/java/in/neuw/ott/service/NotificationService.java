package in.neuw.ott.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static in.neuw.ott.utils.CommonUtils.maskEmail;

@Slf4j
@Service
public class NotificationService {

    @Value("${mail.from}")
    private String emailFrom;

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @SneakyThrows
    public void sendMagicLinkNotification(String userEmail, String url) {
        MimeMessage message = mailSender.createMimeMessage();

        var title = "Welcome!";

        message.setFrom(new InternetAddress(emailFrom));
        message.setSubject(title);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

        String htmlContent = "<h1>Here is the link for proceeding with the app.</h1>" +
                "<a href='"+url+"'>"+url+"</p>";
        message.setContent(htmlContent, "text/html; charset=utf-8");

        // logging the user email is not a good idea at all, but doing it only for debugging purpose!
        log.info("Sending to url {} to user {}", url, maskEmail(userEmail));

        mailSender.send(message);
    }

}