package com.cartmicroservice.project.service;

import com.cartmicroservice.project.entities.CartProduct;
import com.cartmicroservice.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(List<CartProduct> orderProducts,  AtomicReference<Integer> totalPrice, User user, String newDate) {

        Context context = new Context();

        context.setVariable("orderProductsList", orderProducts);
        context.setVariable("totalPrice", totalPrice);
        context.setVariable("userInfo", user);
        context.setVariable("orderDate", newDate);

        return templateEngine.process("mailTemplate", context);

    }

}
