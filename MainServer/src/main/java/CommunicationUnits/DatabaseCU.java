package CommunicationUnits;
import org.json.JSONObject;

import java.io.*;
import java.net.*;


public class DatabaseCU {
    public static String requestLogin(String user, String password) throws IOException, InterruptedException {
        String loginData=user + password;
        URL obj = new URL("localhost:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setDoOutput(true);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(loginData);
        send.close();

        String loginStatus=null;
        int responseCode = postConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            loginStatus=null;

        }

        return loginStatus;
    }

    public static JSONObject databaseRequestReviews(JSONObject bookInformation) throws IOException {
        JSONObject jsonResponse = null;
       // URL obj = new URL(new URL("http://localhost:9595/"), "test/");
        URL obj = new URL("http://reviewinatorserver.chickenkiller.com:6969/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setDoOutput(true);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(bookInformation.toString());
        send.close();

        int responseCode = postConnection.getResponseCode();
        System.out.println("Database response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            jsonResponse = new JSONObject(response.toString());

        }

        return jsonResponse;
    }

    //am zis ca trimitem o lista de JSONuri reviewurile.We dont know yet
    public static JSONObject sendReviews(JSONObject reviews) throws IOException, InterruptedException {
        URL obj = new URL(new URL("http://localhost:9595/"), "test/");
        JSONObject returnJson = null;
//        URL obj = new URL("localhost:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setDoOutput(true);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(reviews.toString());
        send.close();
        int responseCode = postConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            returnJson = new JSONObject(response.toString());

        }
        return returnJson;
    }
}
