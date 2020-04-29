package CommunicationUnits;
import org.json.JSONObject;

import java.io.*;
import java.net.*;


public class DatabaseCU {
    public static String requestLogin(String user, String password) throws IOException, InterruptedException {
        String loginData=user + password;
        URL obj = new URL("http://reviewinatorserver.chickenkiller.com:6969/test");
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

    public static JSONObject databaseRequestReviews(JSONObject bookInformation) throws IOException, InterruptedException {
        JSONObject jsonResponse = null;
        URL obj = new URL("http://reviewinatorserver.chickenkiller.com:6969/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setDoOutput(true);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(bookInformation.toString());
        send.close();


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
            jsonResponse = new JSONObject(response);

        }

        return jsonResponse;
    }

    //am zis ca trimitem o lista de JSONuri reviewurile.We dont know yet
    public static int sendReviews(JSONObject reviews) throws IOException, InterruptedException {
        URL obj = new URL("http://reviewinatorserver.chickenkiller.com:6969/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setDoOutput(true);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(reviews.toString());
        send.close();
        int responseOperation = 0;
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
            responseOperation = Integer.parseInt(response.toString());

        }
        return responseOperation;
    }
}
