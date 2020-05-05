package serverConnection;

import DAO.*;
import bdConnection.DataBaseConnection;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dbObjects.Book;
import dbObjects.History;
import dbObjects.Review;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class MyHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if ("POST".equals(httpExchange.getRequestMethod())) {
            handlePostRequest(httpExchange);
        }
    }

    private void handlePostRequest(HttpExchange httpExchange) throws IOException {
        StringBuilder argument = new StringBuilder();
        int ch = 0;
        while (true) {
            try {
                if (!((ch = httpExchange.getRequestBody().read()) > 0)) break;
            } catch (IOException e) {
                //Display error message
                File file = new File("messages.txt");
                //Create the file
                file.createNewFile();
                String message = e.getMessage();

                BufferedWriter writer2 = new BufferedWriter(
                        new FileWriter("messages.txt", true)
                );
                writer2.write(message + "\n");
                writer2.close();
            }
            argument.append((char) ch);
        }
        JSONObject jsonObject = new JSONObject(argument.toString());
        String method = (String) jsonObject.get("method");
        if (method.substring(0, 3).equals("add")) {
            JSONObject json = (JSONObject) jsonObject.get("argument");
            executeRequestedMethod(httpExchange, method, json);
        } else {
            String getArgument = (String) jsonObject.get("argument");
            handleGetRequest(httpExchange, method, getArgument);
        }
    }

    private void executeRequestedMethod(HttpExchange httpExchange, String requestedMethod, JSONObject jsonObject) throws IOException {
        String fullPath = "src\\config.json";
        String jsonContents = new String((Files.readAllBytes(Paths.get(fullPath))));
        JSONObject obj = new JSONObject(jsonContents);
        String url = (String)obj.get("url");
        String user = (String)obj.get("user");
        String password = (String)obj.get("password");
        Database database = Database.getDatabaseInstance(url, user, password);
        String response = "";

        switch (requestedMethod) {
            case "addBook":
                new BookDAO(database.connection).addBook(jsonObject.getString("title"), jsonObject.getString("author"), jsonObject.getString("genre"), jsonObject.getInt("pageNr"), jsonObject.getString("isbn"), jsonObject.getDouble("avgRating"), jsonObject.getString("resume"));
                break;
            case "addHistory":
                new HistoryDAO(database.connection).addHistory(jsonObject.getDouble("userId"), jsonObject.getInt("visitedBookId"), jsonObject.getString("visitationDate"));
                break;
            case "addReview":
                new ReviewDAO(database.connection).addReview(jsonObject.getDouble("bookId"), (byte) jsonObject.getInt("rating"), jsonObject.getString("reviewText"));
                break;
            case "addUser":
                new UserDAO(database.connection).addUser(jsonObject.getString("name"), jsonObject.getString("lastName"), jsonObject.getString("nickname"), jsonObject.getString("password"), jsonObject.getString("email"));
                break;
            default:
                response = "Not a valid post method!";
        }
        httpExchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    private void handleGetRequest(HttpExchange httpExchange, String requestedMethod, String requestParamValue) throws IOException {
        String fullPath = "src\\config.json";
        String jsonContents = new String((Files.readAllBytes(Paths.get(fullPath))));
        JSONObject obj = new JSONObject(jsonContents);
        String url = (String)obj.get("url");
        String user = (String)obj.get("user");
        String password = (String)obj.get("password");
        Database database = Database.getDatabaseInstance(url, user, password);
        String response = "";


        switch (requestedMethod) {
            case "getBookByTitle":
                response = new JSONObject(new BookDAO(database.connection).getBookByTitle(requestParamValue)).toString();
                break;
            case "getBookByISBN":
                response = new JSONObject(new BookDAO(database.connection).getBookByISBN(requestParamValue)).toString();
                break;
            case "getBooksByAuthor":
                List<Book> books = new BookDAO(database.connection).getBooksByAuthor(requestParamValue);
                for (Book book : books) {
                    response += new JSONObject(book).toString();
                    response += "~";
                }
                break;
            case "getBooksByGenre":
                List<Book> books2 = new BookDAO(database.connection).getBooksByGenre(requestParamValue);
                for (Book book : books2) {
                    response += new JSONObject(book).toString();
                    response += "~";
                }
                break;
            case "deleteBookByISBN":
                new BookDAO(database.connection).deleteBookByISBN(requestParamValue);
                response = "Book " + requestParamValue + " deleted";
                break;
            case "getHistoryByUserId":
                List<History> histories = new HistoryDAO(database.connection).getHistoryByUserId(Double.valueOf(requestParamValue));
                for (History history : histories) {
                    response += new JSONObject(history).toString();
                    response += "~";
                }
                break;
            case "deleteHistoryByUserId":
                new HistoryDAO(database.connection).deleteHistoryByUserId(Double.valueOf(requestParamValue));
                response = "History " + requestParamValue + " deleted";
                break;
            case "getReviewsByBookID":
                List<Review> reviews = new ReviewDAO(database.connection).getReviewsByBookID(Integer.valueOf(requestParamValue));
                for (Review review : reviews) {
                    response += new JSONObject(review).toString();
                }
                break;
            case "getLatestReviewByBookID":
                response = new JSONObject(new ReviewDAO(database.connection).getLatestReviewByBookID(Integer.valueOf(requestParamValue))).toString();
                break;
            case "getAverageRatingByBookId":
                response = String.valueOf(new ReviewDAO(database.connection).getAverageRatingByBookId(Integer.valueOf(requestParamValue)));
                break;
            case "deleteReviewByID":
                new ReviewDAO(database.connection).deleteReviewByID(Double.valueOf(requestParamValue));
                response = "Reviws " + requestParamValue + " deleted";
                break;
            case "getUserByNickname":
                response = new JSONObject(new UserDAO(database.connection).getUserByNickname(requestParamValue)).toString();
                break;
            case "deleteUserByNickname":
                new UserDAO(database.connection).deleteUserByNickname(requestParamValue);
                response = "User " + requestParamValue + " deleted";
                break;
            case "deleteUserByEmail":
                new UserDAO(database.connection).deleteUserByEmail(requestParamValue);
                response = "User " + requestParamValue + " deleted";
                break;
            case "updateUserPassword":
                new UserDAO(database.connection).updateUserPassword(requestParamValue.split("-")[0], requestParamValue.split("-")[1]);
                response = "Password updated for " + requestParamValue.split("-")[0];
                break;
            default:
                response = "";
        }

        try {
            if (response.length() > 0) {
                httpExchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            }else{
                httpExchange.sendResponseHeaders(404, response.getBytes().length);//response code and length
                OutputStream outputStream = httpExchange.getResponseBody();
                response = "Not found. Method or object does not exist!";
                outputStream.write(response.getBytes());
                outputStream.close();
            }

        } catch (IOException e) {
            //Display error message
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = e.getMessage();

            BufferedWriter writer2 = new BufferedWriter(
                    new FileWriter("messages.txt", true)
            );
            writer2.write(message + "\n");
            writer2.close();
        }


    }


}
