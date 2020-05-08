package CommunicationUnits;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class DatabaseCU {
    static HttpClient client;

    public static String requestLogin(String user, String password) throws IOException, InterruptedException {
        String loginData = user + password;
        URL obj = new URL("localhost:9595/test");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setDoOutput(true);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(loginData);
        send.close();

        String loginStatus = null;
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
            loginStatus = null;

        }

        return loginStatus;
    }

    public static JSONObject databaseRequestReviews(JSONObject bookinfo) throws IOException {

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

        request.put("method", "getLatestReviewByBookID");
        // TODO: cand o sa avem metoda getReviewByBookISBN, decomenteaza urmatoarea linie
        //  request.put("argument", bookinfo.get("ISBN"));
        request.put("argument", "1");

        postData=request.toString();
        //System.out.println(postData);
        String response = post(uri, postData);

        JSONObject JsonResponse = new JSONObject(response);
        return JsonResponse;
    }

    static String post(String uri, String data) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();
        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body());
        } catch (IOException e) {


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response.body().toString();
    }
    /*public static JSONObject databaseRequestReviews(JSONObject bookInformation) throws IOException {
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
    }*/
}