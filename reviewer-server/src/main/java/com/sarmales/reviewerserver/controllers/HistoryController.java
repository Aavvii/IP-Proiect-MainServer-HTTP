package com.sarmales.reviewerserver.controllers;

import com.sarmales.reviewerserver.models.HistoryRequest;
import com.sarmales.reviewerserver.models.LoginRequest;
import com.sarmales.reviewerserver.services.DatabaseCU;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user/history")
//{"username":"DanutCiobotaru932"} ..daca are "nickname" ni l da ca empty json {}
public class HistoryController {
    @PostMapping
    public ResponseEntity<String> requestLogin(@RequestBody HistoryRequest request) {
        try {
            JSONObject json = new JSONObject(request);
            System.out.println(json.toString());
            String historyFromDB= DatabaseCU.requestHistory(json);

            String string[] = historyFromDB.split("~");
            List<String> arrayOfReviews = new ArrayList<String>();
            arrayOfReviews = Arrays.asList(string);
            JSONArray ja = new JSONArray();
            for (String s : arrayOfReviews) {
                JSONObject jsonObject = new JSONObject(s);
                ja.put(jsonObject);
            }
            JSONObject mainJson = new JSONObject();
            mainJson.put("history", ja);
            System.out.println(mainJson.toString());
            return ResponseEntity.status(200).body(mainJson.toString());

            // return ResponseEntity.status(200).body(historyFromDB);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
