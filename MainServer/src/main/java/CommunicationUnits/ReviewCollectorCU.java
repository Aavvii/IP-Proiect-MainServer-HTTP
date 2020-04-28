package CommunicationUnits;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import org.json.JSONObject;

import java.io.*;
import java.net.*;

//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;

/**
 * ReviewCollectorCU este responsabil pentru comunicarea cu componenta Review Collector.
 * Se ocupa de primirea si trimiterea de JSON-uri
 * Va trimite un JSON ce reprezinta informatie despre carte: Titlu, Autor, etc
 * Va primi un JSON ce reprezinta review-uri, rating-uri si opinii despre carte
 */
public class ReviewCollectorCU {

    //daca nu exista in baza e date reviewurile, cerem review collectorului sa le genereze, astfel ca-i trimitem autorul si titlul cartii
    //primim reviewurile de la review collector
    public static JSONObject requestReviews(JSONObject bookInformation) throws IOException, InterruptedException {


        JSONObject jsonReviews=null;
        URL obj = new URL("http://reviewinatorserver.chickenkiller.com:6969/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");

        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(bookInformation.toString());
        send.close();

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
            jsonReviews = new JSONObject(response.toString());

        }
        return jsonReviews;
    }
}



