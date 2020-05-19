package com.sarmales.reviewerserver.controllers;

import com.sarmales.reviewerserver.models.MobileAppRequest;
import com.sarmales.reviewerserver.services.MasterCU;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class ReviewsController {
    @PostMapping
    public ResponseEntity<String> getReviews (@RequestBody MobileAppRequest request) {

        try {
           // System.out.println(request.getEncoding());
            JSONObject json = new JSONObject(request);
           // System.out.println(json.toString());
            MasterCU communication = new MasterCU(json.toString());
            communication.prepareResponse();
            JSONObject response = new JSONObject(communication.getOutput());
            if (response.has("mesajEroare")) {
                int statusCode = Integer.parseInt(response.get("responseCode").toString());
                String errMsg = response.toString();
                return ResponseEntity.status(statusCode).body(errMsg);
            }
            else {
                return ResponseEntity.ok(response.toString());
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
