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

    public void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Bienvenue sur Freely");
        String text = "Bonjour " + user.getFirstName() + ",\n\n" +
                "Votre compte est actuellement en attente de validation. \n\n" +
                "Vous recevrez un email lorsque celui ci sera validé. \n\n" +
                "Nous sommes ravis de vous compter parmi les nombreux utilisateurs de Freely. \n" +
                "Merci,\n" +
                "Votre équipe Freely";
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendAccountActivatedEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Votre compte a été activé");

        String text = "Bonjour " + user.getFirstName() + ",\n\n" +
                "Votre compte a été activé par un administrateur. Vous pouvez vous connecter à votre espace en cliquant sur le lien ci-dessous :\n\n" +
                "http://localhost:4200/login\n\n" +
                "Nous sommes ravis de vous compter parmi les nombreux utilisateurs de Freely. \n" +
                "Merci,\n" +
                "Votre équipe Freely";
        message.setText(text);

        javaMailSender.send(message);
    }

    public void sendAccountNotValidatedEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Votre compte n'a pas été validé");

        String text = "Bonjour " + user.getFirstName() + ",\n\n" +
                "Votre compte n'a pas été validé par l'administrateur. pour plus de précision, \n\n" +
                "Vous pouvez le contacter en cliquant sur le lien ci-dessous : \n\n" +
                "http://localhost:4200/contact\n\n" +
                "Merci,\n" +
                "Votre équipe Freely";
        message.setText(text);
        javaMailSender.send(message);
    }
}
