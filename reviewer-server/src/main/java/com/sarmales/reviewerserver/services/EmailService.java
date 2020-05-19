package com.sarmales.reviewerserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailService{
    private JavaMailSender javaMailSender;
    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendEmail(String userEmail) throws MailException{
        /* if(validateEmail(userEmail)) {*/
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(userEmail);
        msg.setFrom("reviewinator.noreply@gmail.com");
        msg.setSubject("Thank you for choosing ReviewInator!");
        msg.setText("Welcome to ReviewInator! \n  \n This is a confirmation mail for your registration. \n" +
                "Please do not reply to this email!\n \n       For any info contact the Sarmale++ Team!");

        javaMailSender.send(msg);
      /*  }
        else
            System.out.println("Email invalid");*/

    }

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;


    public boolean validateEmail(final String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
