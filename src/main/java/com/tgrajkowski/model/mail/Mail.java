package com.tgrajkowski.model.mail;

import com.tgrajkowski.model.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
    private List<ProductDto> newProductOffer = new ArrayList<>();
    private String userName;
    private String confirmCode;
    private MailType mailType;
    private Date createDate;
    private String explain;
    private String welcome;
    private String linkConfirm;
    private boolean confirmAccount = false;
    private String template;
    private String fragment;
    private ProductDto productDto;

    public Mail(String mailTo, String subject, String message, MailType mailType) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
        this.mailType = mailType;
    }

    public Mail(String mailTo, String subject, MailType mailType) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.mailType = mailType;
    }

    public Mail(String mailTo, String subject) {
        this.mailTo = mailTo;
        this.subject = subject;
    }
}
