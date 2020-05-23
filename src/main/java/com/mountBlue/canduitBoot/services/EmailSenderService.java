package com.mountBlue.canduitBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {

    private JavaMailSender javaMailSender; /// No bean found

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        System.out.println("--Calling EmailSenderService Constructor"); //
        System.out.println("--Creating constructor injection here on JavaMailSender"); //
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        System.out.println("--Callign sendEmail method from EmailSenderService"); //
        System.out.println("-- EmailId : " + email); // from=kkrkuldeepkumar@gmail.com; replyTo=null; to=kkrkuldeepkumar02@gmail.com; cc=; bcc=; sentDate=null; subject=Complete Registration!; text=To confirm your account, please click here : http://localhost:8080/confirm-account?token=38e14ae6-dfa0-44bc-a243-eed55efafef7
        javaMailSender.send(email);
        System.out.println("--Sending email");
    }
}
