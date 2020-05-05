package serverConnection;

import DAO.BookDAO;
import DAO.HistoryDAO;
import DAO.ReviewDAO;
import DAO.UserDAO;
import dbObjects.Book;
import dbObjects.History;
import dbObjects.Review;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Client {
    static HttpClient client;

    public static void main(String[] args) throws IOException {
        client = HttpClient.newHttpClient();
        String uri = "http://localhost:9595/test";


        JSONObject request = new JSONObject();
        String post = null;

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
        System.out.println();
        request.put("method", "getLatestReviewByBookID");
        request.put("argument", "1");
        post = request.toString();
        post(uri, post);


    }

    static void post(String uri, String data) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
