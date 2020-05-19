package com.sarmales.reviewerserver.services;

//import com.validator.ErrorHandling;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class DatabaseCU {
    static HttpClient client;

    public static void requestLogin(JSONObject in) throws IOException {

        URL obj = new URL("http://92.80.203.112:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setConnectTimeout(3000);
        postConnection.setDoOutput(true);
        in.put("method", "getUserByNickname");
        in.put("argument", in.get("username"));
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(in.toString());
        send.close();

        int responseCode = postConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
            input.close();

            JSONObject out = new JSONObject(response.toString());

            if (out.get("password").toString().equals(in.get("password").toString())) {
                in.put("responseCode", responseCode);
            }
            else in.put("responseCode", 404);
        }

        else {
            in.put("responseCode", responseCode);
        }
    }

    public static boolean singUpToDatabase (JSONObject in) throws IOException {
        URL obj = new URL("http://92.80.203.112:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setConnectTimeout(3000);
        postConnection.setDoOutput(true);
        JSONObject requestJson = new JSONObject();
        requestJson.put("method", "addUser");
        requestJson.put("argument", in);
        System.out.println(requestJson.toString());
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(requestJson.toString());
        send.close();

        int responseCode = postConnection.getResponseCode();

        postConnection.getInputStream().close();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            return true;
        }
        else {
            return false;
        }
    }

    public static String requestHistory(JSONObject jsonObject) throws IOException {
        String response = null;
        URL obj = new URL("http://92.80.203.112:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setConnectTimeout(5000);
        postConnection.setDoOutput(true);
        JSONObject requestJson = new JSONObject();
//        { "method":"getHistoryByUserNickname",
//                "argument":"maria123"}//DanutCiobotaru932
        System.out.println(jsonObject.toString());
        requestJson.put("method", "getAllBooksReviewsByNickname");
        System.out.println("argument"+jsonObject.get("nickname").toString());//ok
        requestJson.put("argument", jsonObject.get("nickname").toString());//in fct de ce primesc de la mobile

        System.out.println(requestJson.toString());
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(requestJson.toString());
        send.close();

        System.out.println("aici");
        int responseCode = 0;
        responseCode = postConnection.getResponseCode();
        System.out.println("database history response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String inputLine = null;
            StringBuffer responseSB = new StringBuffer();
            while (true) {
                if (!((inputLine = in.readLine()) != null)) break;
                responseSB.append(inputLine);
                String error = responseSB.substring(2, 7);
                if (error.equals("error")) {
                    //trimitere eroare la mobile app
                }
            }
            System.out.println("response historyDB: " + responseSB);
            response=responseSB.toString();
            in.close();
        }
        return response;
    }


    public static JSONObject databaseRequestReviews(JSONObject jsonObject) throws IOException {
        JSONObject json = new JSONObject("{\"responseCode\" : 200}");

        URL obj = new URL("http://92.80.203.112:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setConnectTimeout(3000);
        postConnection.setDoOutput(true);
        json.put("method", "getReviewsByBookISBN");
        String bookIsbn = jsonObject.get("ISBN").toString();
        json.put("argument", bookIsbn);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(json.toString());
        send.close();

        json.remove("method"); json.remove("argument");
        int responseCode = postConnection.getResponseCode();
        json.put("responseCode", responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
            input.close();

            String rating = getRating(bookIsbn);
            json = formatJson(response.toString(), rating);
            json.put("responseCode", responseCode);
        }
        return json;
    }

    public static boolean addBook(String isbn, String rating) throws IOException {
        JSONObject in = new JSONObject();
        URL obj = new URL("http://92.80.203.112:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setConnectTimeout(3000);
        postConnection.setDoOutput(true);

        JSONObject bookInfo = new JSONObject();
        bookInfo.put("title", "ceva");
        bookInfo.put("author", "Smecheru");
        bookInfo.put("genre", "nic");
        bookInfo.put("pageNr", 2);
        bookInfo.put("isbn", isbn);
        bookInfo.put("avgRating", Float.parseFloat(rating));
        bookInfo.put("resume", "ok");

        in.put("method", "addBook");
        in.put("argument", bookInfo);

        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(in.toString());
        send.close();

        int responseCode = postConnection.getResponseCode();
        postConnection.getInputStream().close();
        //success
        return responseCode == HttpURLConnection.HTTP_OK;
    }

    public static boolean addHistory(int userId, int bookId) throws IOException {
        JSONObject in = new JSONObject();
        URL obj = new URL("http://92.80.203.112:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setConnectTimeout(3000);
        postConnection.setDoOutput(true);

        JSONObject historyInfo = new JSONObject();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date();
        historyInfo.put("userId", userId);
        historyInfo.put("visitedBookId", bookId);
        historyInfo.put("visitationDate", df.format(today));

        in.put("method", "addHistory");
        in.put("argument", historyInfo);

        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(in.toString());
        send.close();

        int responseCode = postConnection.getResponseCode();
        postConnection.getInputStream().close();
        //success
        return responseCode == HttpURLConnection.HTTP_OK;
    }

    public static void addReviews(JSONObject json, int userId, int bookId) throws IOException {
        JSONObject in = new JSONObject();
        URL obj = new URL("http://92.80.203.112:9595/test");
        in.put("method", "addReview");
        JSONArray jsonArray = (JSONArray) json.get("reviews");
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date();
        //Iterating the contents of the array
        for (Object o : jsonArray) {
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("content-type", "application/json");
            postConnection.setConnectTimeout(3000);
            postConnection.setDoOutput(true);
            String userRating = ((JSONObject) o).get("rating").toString();
            float rating;
            if (userRating.equals("Not available")) rating = 0;
            else rating = Float.parseFloat(userRating);
            System.out.println(rating);
            JSONObject reviewInfo = new JSONObject();
            reviewInfo.put("userId", userId);
            reviewInfo.put("bookId", bookId);
            reviewInfo.put("rating", rating);
            reviewInfo.put("reviewText", ((JSONObject) o).get("description").toString());
            reviewInfo.put("date", df.format(today));
            in.put("argument", reviewInfo);
            System.out.println(in.toString());
            BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
            send.write(in.toString());
            send.close();
            postConnection.getResponseCode();
            postConnection.getInputStream().close();
        }
    }

    public static int getUserId(String nickname) throws IOException {
        JSONObject in = new JSONObject();
        URL obj = new URL("http://92.80.203.112:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setConnectTimeout(3000);
        postConnection.setDoOutput(true);
        in.put("method", "getUserByNickname");
        in.put("argument", nickname);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(in.toString());
        send.close();
        int responseCode = postConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
            input.close();

            JSONObject responseJson = new JSONObject(response.toString());
            return (Integer) responseJson.get("id");
        }
        //success
        return -1;
    }

    public static int getBookId(String isbn) throws IOException {
        JSONObject in = new JSONObject();
        URL obj = new URL("http://92.80.203.112:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setConnectTimeout(3000);
        postConnection.setDoOutput(true);
        in.put("method", "getBookByISBN");
        in.put("argument", isbn);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(in.toString());
        send.close();
        int responseCode = postConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
            input.close();

            JSONObject responseJson = new JSONObject(response.toString());
            return (Integer) responseJson.get("id");
        }
        //success
        return -1;
    }

    public static String getRating(String isbn) throws IOException {
        URL obj = new URL("http://92.80.203.112:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setDoOutput(true);
        JSONObject requestJson = new JSONObject();
        requestJson.put("method", "getAverageRatingByBookISBN");
        requestJson.put("argument", isbn);
        //System.out.println(requestJson.toString());
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(requestJson.toString());
        send.close();

        int responseCode = postConnection.getResponseCode();
        // postConnection.getInputStream().close();
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
            }
            System.out.println("ImageProcesorCU response:" + response.toString());
            in.close();
            return response.toString();
        }
        return "error";
    }

    public static JSONObject formatJson(String reviewsDatabase,String rating) {

        String[] str = reviewsDatabase.split("~");
        List<String> al;
        al = Arrays.asList(str);
        JSONArray ja = new JSONArray();
        for (String s : al) {
            JSONObject jsonObject = new JSONObject(s);
            ja.put(jsonObject);
        }
        JSONObject mainJson = new JSONObject();
        mainJson.put("reviews",ja);
        mainJson.put("overall_rating",rating);
        return mainJson;
    }

    static String post(String uri, String data) throws IOException {
        String responseString = null;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();
        HttpResponse response =null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseString=response.body().toString();
            if(response.body().toString().substring(0,23).equalsIgnoreCase("ObjectNotFoundException")){
                responseString ="noReviews";
            }
            if(response.body().toString().equalsIgnoreCase("Method does not exist!")){
                responseString = "internalError";
            }


            //System.out.println(response.body());
        } catch (IOException e) {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(response == null){
            responseString="internalError";
        }
        return responseString;
    }

}
