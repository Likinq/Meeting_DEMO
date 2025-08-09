package com.example.demo.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleOrderManager {
    @Autowired
    JavaMailSender mailSender;

    public String sendMail(String mail) {
        String code = Double.toString(Math.random()).substring(2,8);

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(mail));
            mimeMessage.setFrom(new InternetAddress("chenyin_08261029@163.com"));
            mimeMessage.setText("您的验证码为" + code + "。");
            mimeMessage.setSubject("智能会议室预约管理系统登录验证码");
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
        return code;
    }

}