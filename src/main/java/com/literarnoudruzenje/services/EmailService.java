package com.literarnoudruzenje.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;


    @Async
    public void sendNotification(String email, String message, String subject) throws MailException, InterruptedException {
        System.out.println("Sending email...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject(subject);
        mail.setText(message);
        javaMailSender.send(mail);

        System.out.println("Email sent!");
    }

    public String sendMailToUser(String email, String message, String subject) {
        try {
            sendNotification(email, message, subject);
        } catch (Exception e) {
        }
        return "success";
    }
}
