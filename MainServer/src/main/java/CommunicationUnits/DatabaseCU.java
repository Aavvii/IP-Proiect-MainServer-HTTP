package CommunicationUnits;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
//Importuri care nu sunt necesare dar erau aici
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DatabaseCU este responsabil pentru comunicarea cu componenta Database
 * Se ocupa de primirea si trimiterea de JSON-uri
 */
public class DatabaseCU {
    public static void login(String user, String password) {
        //ne conectam la api-ul special pentru login
    }
    public static JSONObject requestReviews(JSONObject bookInformation) throws IOException, InterruptedException {
        //scriere in api
       /* String requestData = bookInformation.toString();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://szmuschi.pythonanywhere.com/api"))
                .POST(HttpRequest.BodyPublishers.ofString(requestData))
                .build();

        HttpResponse<String> responseJson = null;
        responseJson = client.send(request,
                HttpResponse.BodyHandlers.ofString());*/
        //aceasta parte este un o scriere in api care momentan nu merge

        JSONObject jsonResponse=null;
        URL obj = new URL("https://szmuschi.pythonanywhere.com/api");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setDoOutput(true);
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
            jsonResponse = new JSONObject(response.toString());

        }
        return jsonResponse;

    }
    //am zis ca trimitem o lista de JSONuri reviewurile.We dont know yet
    public static void sendReviews(JSONObject reviews) throws IOException, InterruptedException {
        //scriere in api
        /*List<String> requestData=new ArrayList<>();
        for(JSONObject jsonObject : reviews){
            requestData.add(jsonObject.toString());
        }
        for(String s  : requestData) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://szmuschi.pythonanywhere.com/api"))
                    .POST(HttpRequest.BodyPublishers.ofString(s))
                    .build();

            HttpResponse<String> responseJson = null;
            responseJson = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        }*/
        //aceasta parte este un o scriere in api care momentan nu merge
    }
}
