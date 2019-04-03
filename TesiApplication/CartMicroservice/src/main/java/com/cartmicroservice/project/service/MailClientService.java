package com.cartmicroservice.project.service;

import com.cartmicroservice.project.entities.CartProduct;
import com.cartmicroservice.project.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MailClientService {

    Logger logger = LoggerFactory.getLogger(getClass());
    private JavaMailSender mailSender;
    MailContentBuilder MailContentBuilder;

    @Autowired
    public MailClientService(JavaMailSender mailSender, MailContentBuilder MailContentBuilder) {
        this.mailSender = mailSender;
        this.MailContentBuilder = MailContentBuilder;
    }

    public void prepareAndSend(String recipient, List<CartProduct> orderProducts, AtomicReference<Integer> totalPrice,
                               User user, String newDate) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("Order Summary");

            String content = MailContentBuilder.build(orderProducts, totalPrice, user, newDate);
            messageHelper.setText(content, true);
        };
        try {

            mailSender.send(messagePreparator);

        } catch (MailException e) {
            logger.debug("Mail Exception: " + e.toString());
        }

    }

}
