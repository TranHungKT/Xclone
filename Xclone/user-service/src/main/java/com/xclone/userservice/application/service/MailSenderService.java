package com.xclone.userservice.application.service;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface MailSenderService {
    void sendHtmlEmail(String template, String to, Map<String, Object> attributes, String subject) throws MessagingException;
}
