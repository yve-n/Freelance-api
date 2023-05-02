package com.cda.freely.service;

import com.cda.freely.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendAccountActivatedEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Your account has been activated");

        String text = "Dear " + user.getFirstName() + ",\n\n" +
                "Your account has been activated by the administrator. You can now login to our application using the following link:\n\n" +
                "http://your-application-url.com/login\n\n" +
                "Thank you,\n" +
                "Your application team";
        message.setText(text);

        javaMailSender.send(message);
    }
}
