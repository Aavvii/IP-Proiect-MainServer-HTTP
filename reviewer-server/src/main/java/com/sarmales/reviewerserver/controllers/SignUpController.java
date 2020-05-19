package com.sarmales.reviewerserver.controllers;

import com.sarmales.reviewerserver.models.SignUpRequest;
import com.sarmales.reviewerserver.services.DatabaseCU;
import com.sarmales.reviewerserver.services.EmailService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class SignUpController {
    private Logger logger= LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    EmailService emailService;


    @RequestMapping("/user/signup")
    @PostMapping
    public ResponseEntity<Integer> requestSignUp(@RequestBody SignUpRequest request) {
        try {

            JSONObject json = new JSONObject(request);
            System.out.println(json.toString());
            System.out.println(request.getEmail());
            if (DatabaseCU.singUpToDatabase(json))
            {
                if(emailService.validateEmail(request.getEmail())){
                    try{
                        emailService.sendEmail(request.getEmail());
                        System.out.println("Mail sent!");
                    }
                    catch (MailException e)
                    {logger.info("Error sending email..."+e.getMessage());}
                    return ResponseEntity.ok(1);

                }
                else {
                    return ResponseEntity.status(404).body(0);}
            }
            else {
                return ResponseEntity.status(406).body(0);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
        }
    }

}
