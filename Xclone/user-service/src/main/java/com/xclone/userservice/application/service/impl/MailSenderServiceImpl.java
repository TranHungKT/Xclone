package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.MailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MailSenderServiceImpl implements MailSenderService {
    @Value("${spring.mail.username}")
    private String USER_NAME;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendHtmlEmail(String template, String to, Map<String, Object> attributes ,String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        Context context = new Context();
        context.setVariables(attributes);
        String process = templateEngine.process(template, context);

        MimeMessageHelper mimeMessageHelper  = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom(new InternetAddress(USER_NAME));
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(process, true);
        mailSender.send(message);
    }
}
