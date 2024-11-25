package com.myproject.SpringStarter.Config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class EmailConfig {

    @Value("${mail.transport.protocol}")
    private String mail_transport_protocol;

    @Value("${spring.mail.host}")
    private String spring_mail_host;

    @Value("${spring.mail.port}")
    private String spring_mail_port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String properties_mail_smtp_auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String properties_mail_smtp_starttls_enable;

    @Value("${spring.mail.smtp.ssl.trust}")
    private String smtp_ssl_trust;

    @Value("${spring.mail.username}")
    private String spring_mail_username;

    @Value("${spring.mail.password}")
    private String spring_mail_password;

    
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(spring_mail_host);
        mailSender.setPort(Integer.parseInt(spring_mail_port));

        mailSender.setUsername(spring_mail_username);
        mailSender.setPassword(spring_mail_password);

        Properties property = mailSender.getJavaMailProperties();
        property.put("mail.transport.protocol", mail_transport_protocol);
        property.put("mail.smtp.auth", properties_mail_smtp_auth);
        property.put("mail.smtp.starttls.enable", properties_mail_smtp_starttls_enable);
        property.put("mail.smtp.ssl.trust",smtp_ssl_trust);
        property.put("mail.debug", true);

        return mailSender;
    }
}
