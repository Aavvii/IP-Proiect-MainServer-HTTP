package CommunicationUnits;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.validator.ErrorHandling;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class DatabaseCU {
    static HttpClient client;

    public static void requestLogin(JSONObject in) throws IOException {

        URL obj = new URL("http://87.255.79.195:7532/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
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
            } else in.put("responseCode", 404);
        } else {
            in.put("responseCode", responseCode);
        }
    }

    public static boolean singUpToDatabase(JSONObject in) throws IOException {
        URL obj = new URL("http://87.255.79.195:7532/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
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
        } else {
            return false;
        }
    }

    public static JSONObject requestHistory(JSONObject jsonObject) throws IOException {
        JSONObject jsonResponse = null;
        try {
            URL obj = new URL("http://87.255.79.195:7532/test");
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("content-type", "application/json");
            postConnection.setDoOutput(true);
            JSONObject requestJson = new JSONObject();
//        { "method":"getHistoryByUserNickname",
//                "argument":"maria123"}//DanutCiobotaru932
            requestJson.put("method", "getHistoryByUserNickname");
            requestJson.put("argument", jsonObject.get("username").toString());//in fct de ce primesc de la mobile
            BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
            send.write(requestJson.toString());
            send.close();

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
                if (responseSB.toString().equals("")) {
                    jsonResponse = new JSONObject();
                    jsonResponse.put("mesajEroare", "Eroare interna");
                    jsonResponse.put("responseCode", "406");
                } else {
                    if (ErrorHandling.isValid(responseSB.toString())) {
                        jsonResponse = new JSONObject(responseSB.toString());
                        System.out.println(jsonResponse.toString());
                        if (ErrorHandling.isJsonEmpty(jsonResponse, "reviews") || !jsonResponse.has("reviews")) {
                            jsonResponse = new JSONObject();
                            jsonResponse.put("mesajEroare", "Nu s-au gasit review-uri");
                            jsonResponse.put("responseCode", "406");
                        }
                    }
                }
            } else {
                jsonResponse = new JSONObject();
                jsonResponse.put("mesajEroare", "Eroare interna");
                jsonResponse.put("responseCode", "406");
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    public static JSONObject databaseRequestReviews(JSONObject jsonObject) throws IOException {

        client = HttpClient.newHttpClient();
        String uri = "http://localhost:9595/test";


        JSONObject request = new JSONObject();
        String postData = null;

        /*System.out.println();
        request.put("method", "getBookByISBN");
        request.put("argument", "341-497-344-670-2");
        post = request.toString();
        System.out.println("  getBookByISBN ( 341-497-344-670-2 )");
        post(uri, post);

        System.out.println();
        request.put("method", "getBooksByAuthor");
        request.put("argument", "Enid Blyton");
        post = request.toString();
        System.out.println("  getBooksByAuthor ( Enid Blyton )");
        post(uri, post);

        System.out.println();
        request.put("method", "getBooksByGenre");
        request.put("argument", "Fictiune");
        post = request.toString();
        System.out.println("  getBooksByGenre ( Fictiune )");
        post(uri, post);

        System.out.println();
        request.put("method", "deleteBookByISBN");
        request.put("argument", "124-948-876-537-2");
        post = request.toString();
        System.out.println("  deleteBookByISBN ( 124-948-876-537-2 )");
        post(uri, post);
*/
        if (jsonObject.has("ISBN")) {
            request.put("method", "getLatestReviewByBookID");
            // TODO: cand o sa avem metoda getReviewByBookISBN, decomenteaza urmatoarea linie
            //  request.put("argument", bookinfo.get("ISBN"));
            request.put("argument", "1");
        } else {
            if (jsonObject.has("reviews")) {
                //trimitem reviews
                //TODO facem functie aici sau separat?
            }
        }
        JSONObject JsonResponse = null;
        postData = request.toString();
        //System.out.println(postData);
        String response = post(uri, postData);
        if (response.equalsIgnoreCase("internalError") || response.equalsIgnoreCase("noReviews")) {
            JsonResponse = new JSONObject("{}");
        }
        if (ErrorHandling.isValid(response)) {
            JsonResponse = new JSONObject(response);
            if (ErrorHandling.isJsonEmpty(JsonResponse, "reviews") || !JsonResponse.has("reviews")) {
                JsonResponse = new JSONObject("{}");
            }
        }


        return JsonResponse;
    }
    // TODO FUNCTIE PENTRU TRIMIS REVIEWS -SEPARAT?

    static String post(String uri, String data) throws IOException {
        String responseString = null;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();
        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseString = response.body().toString();
            if (response.body().toString().substring(0, 23).equalsIgnoreCase("ObjectNotFoundException")) {
                responseString = "noReviews";
            }
            if (response.body().toString().equalsIgnoreCase("Method does not exist!")) {
                responseString = "internalError";
            }


            //System.out.println(response.body());
        } catch (IOException e) {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response == null) {
            responseString = "internalError";
        }
        return responseString;
    }

}
