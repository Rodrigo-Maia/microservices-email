package com.rodrigomaia.retratemeemail.services;

import com.rodrigomaia.retratemeemail.enums.EnumStatusEmail;
import com.rodrigomaia.retratemeemail.models.EmailModel;
import com.rodrigomaia.retratemeemail.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {

    final EmailRepository emailRepository;
    final JavaMailSender javaEmailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender javaEmailSender) {
        this.emailRepository = emailRepository;
        this.javaEmailSender = javaEmailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel)
    {
        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            javaEmailSender.send(message);

            emailModel.setStatusEmail(EnumStatusEmail.SENT);
        } catch (MailException e){
            emailModel.setStatusEmail(EnumStatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
