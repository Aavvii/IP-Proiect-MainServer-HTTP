package CommunicationUnits;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//daca nu exista in baza e date reviewurile, cerem review collectorului sa le genereze, astfel ca-i trimitem autorul si titlul cartii
//primim reviewurile de la review collector
public class ReviewCollectorCU {


    public static JSONObject requestReviews(JSONObject bookInformation) throws IOException, InterruptedException {
        JSONObject jsonReviews=null;
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
            System.out.println(response);
            jsonReviews=new JSONObject(response.toString());
            System.out.println(jsonReviews.toString());

        }

        return jsonReviews;
    }
}
