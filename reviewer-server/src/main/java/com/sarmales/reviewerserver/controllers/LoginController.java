package com.sarmales.reviewerserver.controllers;
import com.sarmales.reviewerserver.models.LoginRequest;
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
@RequestMapping("/user/login")

public class LoginController {

    @PostMapping
    public ResponseEntity<Integer> requestLogin(@RequestBody LoginRequest request) {
        try {
            JSONObject json = new JSONObject(request);
//            System.out.println(json.toString());
            DatabaseCU.requestLogin(json);
            int responseCode = (int) json.get("responseCode");
            if (responseCode == 200) {
                return ResponseEntity.status(responseCode).body(1);
            }
            else {
                return ResponseEntity.status(responseCode).body(0);
//            System.out.println(json.toString())
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
        }
    }
}
