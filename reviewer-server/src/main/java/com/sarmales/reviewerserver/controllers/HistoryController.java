package com.sarmales.reviewerserver.controllers;

import com.sarmales.reviewerserver.models.HistoryRequest;
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
@RequestMapping("/user/history")
public class HistoryController {
    @PostMapping
    public ResponseEntity<String> requestLogin(@RequestBody HistoryRequest request) {
        try {
            JSONObject json = new JSONObject(request);
            String historyFromDB= DatabaseCU.requestHistory(json);
            int responseCode = Integer.parseInt(json.get("responseCode").toString());
            //de verificat response code
            System.out.println(json.toString());
            return ResponseEntity.status(responseCode).body(historyFromDB);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
