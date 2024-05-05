package com.xclone.userservice.configuration.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String HOST;

    @Value("${spring.mail.port}")
    private Integer PORT;

    @Value("${spring.mail.username}")
    private String USER_NAME;


    @Value("${spring.mail.password}")
    private String PASSWORD;

    @Value("${spring.mail.protocol}")
    private String PROTOCOL;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String AUTH;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String STARTTLS_ENABLE;

    @Value("${mail.debug}")
    private String DEBUG;

    public static final String EMAIL_TEMPLATE_CODING = "UTF-8";

    @Bean
    JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(HOST);
        mailSender.setPort(PORT);
        mailSender.setUsername(USER_NAME);
        mailSender.setPassword(PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.debug", DEBUG);
        props.setProperty("mail.smtp.auth", AUTH);
        props.setProperty("mail.smtp.starttls.enable", STARTTLS_ENABLE);
        return mailSender;
    }

}
