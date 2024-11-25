package com.myproject.SpringStarter.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.myproject.SpringStarter.Until.Email.EmailData;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    public boolean sendSimpleEmail(EmailData emailData) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(username);
            mailMessage.setTo(emailData.getReciptient());
            mailMessage.setText(emailData.getMessageBody());
            System.out.println("Sended email from "+username+" to "+ emailData.getReciptient() + "| Content is "+ emailData.getMessageBody());

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }
}
