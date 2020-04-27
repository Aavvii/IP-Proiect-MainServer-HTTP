package CommunicationUnits;

import org.json.JSONObject;

import java.util.List;

/**
 * MobileAppCU este responsabil pentru comunicarea cu componenta Mobile App
 * Se ocupa de primirea si trimiterea de JSON-uri
 */
public class MobileAppCU {

    public JSONObject receiveAndSendImageJson(JSONObject imageJson) throws IOException, InterruptedException {
        //scrierea in api - trimiterea jsonului
        /*String requestData = imageJson.toString();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://szmuschi.pythonanywhere.com/api"))
                .POST(HttpRequest.BodyPublishers.ofString(requestData))
                .build();
        HttpResponse<String> responseJson = null;
        responseJson = client.send(request,
                HttpResponse.BodyHandlers.ofString());*/

        JSONObject jsonResponse=null;
        URL obj = new URL("https://szmuschi.pythonanywhere.com/api");//api ul celor de la mobile app
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "image/jpeg");//application/json
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

    public void sendReviews(List<JSONObject> reviews) {
        List<String> requestData = new ArrayList<>();
        for(JSONObject jo : reviews){ //populare lista cu stringuri din json
            requestData.add(jsonObject.toString());
        }
        for(String string_json : requestData){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://szmuschi.pythonanywhere.com/api"))//api-ul celor de la mobile app
                    .POST(HttpRequest.BodyPublishers.ofString(s))
                    .build();
            HttpResponse<String> responseJson = null;
            responseJson = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        }
    }
}
