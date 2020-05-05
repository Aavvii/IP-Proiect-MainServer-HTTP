package CommunicationUnits;

import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Componenta ce va face legatura intre toate celelalte Communication Units
 * Aici vor va fi manipulata informatia primita de la si trimisa la celelalte componente are aplicatiei.
 * Vor fi apelate functii din celelate CU-uri.
 * In momentul actual codul este de la partea de socket-uri - si nu este util.
 */

public class MasterCU {

    private String input;
    private String output;

    public void prepareResponse() {

//        JSONObject inputJSON = new JSONObject(input);
//        JSONObject bookInfo = ImageProcessorCU.requestBookInfo(inputJSON);
        JSONObject bookInfo = new JSONObject("{\"ISBN\": \"978-606-623-2\", \"author\": \"JRR Tolkien\", \"message\": \"decoded\", \"success\": \"DA\", \"title\": \"Silmarilion\"}");
        JSONObject dbReviewsResponse = null;
        System.out.println("bookInfo"+bookInfo);
//        try {
//            //dbReviewsResponse = DatabaseCU.databaseRequestReviews(bookInfo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println("dbReviewsResponse"+dbReviewsResponse);
        JSONObject bookReviews = null;
       // if ("{}".equals(dbReviewsResponse.toString())) {
        if(dbReviewsResponse.toString().equals(null)){
            try {
                bookReviews = ReviewCollectorCU.requestReviews(bookInfo);
                DatabaseCU.sendReviews(bookReviews);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }else {
            bookReviews = dbReviewsResponse;
        }
        output = bookReviews.toString();
    }

    public MasterCU(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

}