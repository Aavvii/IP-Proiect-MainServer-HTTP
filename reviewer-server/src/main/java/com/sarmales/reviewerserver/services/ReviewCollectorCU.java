package com.sarmales.reviewerserver.services;


import org.json.JSONObject;
import com.sarmales.reviewerserver.handler.ErrorHandling;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//daca nu exista in baza e date reviewurile, cerem review collectorului sa le genereze, astfel ca-i trimitem autorul si titlul cartii
//primim reviewurile de la review collector

public class ReviewCollectorCU {

    public static JSONObject requestReviews(JSONObject bookInformation) throws IOException, InterruptedException {
        JSONObject jsonResponse = null;
        URL obj = null;
        String ISBN=null;
        try {
            System.out.println("bookInformation: " + bookInformation);//ok
             ISBN = bookInformation.get("ISBN").toString();
            ISBN = ISBN.replaceAll("[^0-9]", "");
            System.out.println("isbn : " + ISBN);
            obj = new URL("http://stefanbeleuz.pythonanywhere.com/review/goodreads?isbn=" + ISBN);//ok
            //obj = new URL("http://stefanbeleuz.pythonanywhere.com/review/goodreads?isbn=9780060920432");
            System.out.println("obj: " + obj.toString());
            HttpURLConnection postConnection;
            postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("GET");
            postConnection.setRequestProperty("content-type", "application/json");

            int responseCode = 0;
            responseCode = postConnection.getResponseCode();
            System.out.println("ReviewCU response code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inputLine = null;
                StringBuffer response = new StringBuffer();
                while (true) {
                    if (!((inputLine = in.readLine()) != null)) break;
                    response.append(inputLine);
                    /*String error = response.substring(2, 7);
                    if (error.equals("error")) {
                        //trimitere eroare la mobile app
                    }*/
                }
                System.out.println("response api review: " + response);
                in.close();
                if(response.toString().equals("")) {
                    jsonResponse =new JSONObject();
                    jsonResponse.put("mesajEroare","Nu s-au gasit review-uri pentru ISBN-ul : " + ISBN);
                    jsonResponse.put("ISBN",ISBN);
                    jsonResponse.put("responseCode","411");
                }else {
                    if(ErrorHandling.isValid(response.toString())) {
                        jsonResponse = new JSONObject();
                         jsonResponse.put("isbn",ISBN);
                        JSONObject json1 = new JSONObject(response.toString());
                        jsonResponse.put("overall_rating",json1.get("overall_rating"));
                       jsonResponse.put("reviews", json1.get("reviews"));
                        System.out.println(jsonResponse.toString());
                        if(ErrorHandling.isJsonEmpty(jsonResponse,"reviews") || !jsonResponse.has("reviews")){
                            jsonResponse =new JSONObject();
                            jsonResponse.put("mesajEroare","Nu s-au gasit review-uri pentru ISBN-ul : " + ISBN);
                            jsonResponse.put("ISBN",ISBN);
                            jsonResponse.put("responseCode","411");
                        }
                    } else{
                        jsonResponse =new JSONObject();
                        jsonResponse.put("mesajEroare","Nu s-au gasit review-uri pentru ISBN-ul : " + ISBN);
                        jsonResponse.put("ISBN",ISBN);
                        jsonResponse.put("responseCode","411");
                    }
                }
            }else{
                jsonResponse =new JSONObject();
                jsonResponse.put("mesajEroare", "Eroare conexiune Review Collector.");
                jsonResponse.put("responseCode","410");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }
}
