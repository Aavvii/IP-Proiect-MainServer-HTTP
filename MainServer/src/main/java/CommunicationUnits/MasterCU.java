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

        JSONObject inputJSON = new JSONObject(input);
        JSONObject bookInfo = ImageProcessorCU.requestBookInfo(inputJSON);
        JSONObject dbReviewsResponse = null;
        try {
            dbReviewsResponse = DatabaseCU.databaseRequestReviews(bookInfo);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject bookReviews = null;
        if ("{}".equals(dbReviewsResponse.toString())) {
            try {
                bookReviews = ReviewCollectorCU.requestReviews(bookInfo);
                DatabaseCU.sendReviews(bookReviews);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
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