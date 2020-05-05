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
//http://reviewinatorserver.chickenkiller.com:6969/test
//DB: {
//	"method":"getBooksByGenre"
//	"argument":"978-909-320-212"
//}

public class MasterCU {

    private String input;
    private String output;

    public void prepareResponse() {
        JSONObject inputJSON = new JSONObject(input);
        //JSONObject bookInfo = ImageProcessorCU.requestBookInfo(inputJSON);
//Caz 1: eroare la beleuz
        //JSONObject bookInfo = new JSONObject("{\"ISBN\": \"978-606-623-2\", \"author\": \"JRR Tolkien\", \"message\": \"decoded\", \"success\": \"DA\", \"title\": \"Silmarilion\"}");
        JSONObject bookInfo = new JSONObject("{\"ISBN\": \"978-0135048740\", \"author\": \"JRR Tolkien\", \"message\": \"decoded\", \"success\": \"DA\", \"title\": \"Silmarilion\"}");
//Caz 2: should work
        //JSONObject bookInfo = new JSONObject("{\"ISBN\": \"978-006-092-043-2\", \"author\": \"Andy Mitchell\", \"message\": \"decoded\", \"success\": \"DA\", \"title\": \"\"}");
        JSONObject dbReviewsResponse = null;
//        System.out.println("bookInfo"+bookInfo);
//        try {
//            dbReviewsResponse = DatabaseCU.databaseRequestReviews(bookInfo);
//            System.out.println("aici");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("dbReviewsResponse : "+dbReviewsResponse);
        JSONObject bookReviews = null;
        //if (dbReviewsResponse.toString().equals(null)) {
        if(dbReviewsResponse == null){
            try {
                System.out.println("bookInfo : " + bookInfo);
                bookReviews = ReviewCollectorCU.requestReviews(bookInfo);
                System.out.println("----------------------------------------------- \n bookReviewsBeleuz : " + bookReviews);
               // DatabaseCU.sendReviews(bookReviews);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
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