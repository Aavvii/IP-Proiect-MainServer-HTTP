package com.sarmales.reviewerserver.controllers;

import com.sarmales.reviewerserver.models.SignUpRequest;
import com.sarmales.reviewerserver.services.DatabaseCU;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/user/signup")

public class SignUpController {
    @PostMapping
    public ResponseEntity<Integer> requestSignUp(@RequestBody SignUpRequest request) {
        try {
            JSONObject json = new JSONObject(request);
            System.out.println(json.toString());
            if (DatabaseCU.singUpToDatabase(json))
            {
                return ResponseEntity.ok(1);
            }
            else {
                return ResponseEntity.status(406).body(0);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
        }
    }
}
