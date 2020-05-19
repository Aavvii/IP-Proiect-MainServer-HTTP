package com.sarmales.reviewerserver.services;

import com.sarmales.reviewerserver.handler.ErrorHandling;
import org.json.JSONObject;

import java.io.IOException;

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
    boolean flag = true;
    boolean imgProcFlag = true;
    boolean isbnFound = true;
    JSONObject errorMsg = null;
    JSONObject dbReviewsResponse = null;
    JSONObject bookReviews = null;

    public void prepareResponse() throws IOException, InterruptedException {
        JSONObject inputJSON = new JSONObject(input);
        JSONObject bookInfo = null;
        //Caz1: Nu am trimis un isbn la mobile app : ISBN:"9780735623873" -ok
        if (inputJSON.has("isbn") && !inputJSON.get("isbn").toString().equals("")) {
            System.out.println(" ISBN OK ... EXISTA \n \n ");
            imgProcFlag = false;
            bookInfo = new JSONObject();
            bookInfo.put("ISBN", inputJSON.get("isbn").toString());
            System.out.println(bookInfo.get("ISBN"));

        } else if (ErrorHandling.isValid(input)) {
            System.out.println();
            // System.out.println(inputJSON.toString());

            if (ErrorHandling.isJsonEmpty(inputJSON, "encoding")) {
                flag = false;
                errorMsg = new JSONObject();
                errorMsg.put("mesajEroare", "JSON gol");
                errorMsg.put("responseCode", "405");
            }
        } else {
            errorMsg = new JSONObject();
            flag = false;
            errorMsg.put("mesajEroare", "nu este in format JSON");
            errorMsg.put("responseCode", "402");
        }

        // TODO System.out.println(inputJSON);
        // TODO JSONObject bookInfo = ImageProcessorCU.requestBookInfo(inputJSON);

        JSONObject dbReviewsResponse = null;
        JSONObject bookReviews = null;
        if (flag && imgProcFlag) {
            System.out.println(" FLAGS ENTER ");
            inputJSON.remove("isbn");
            bookInfo = ImageProcessorCU.requestBookInfo(inputJSON.toString());
            if (bookInfo.has("mesajEroare")) {
                flag = false;
                errorMsg = new JSONObject(bookInfo.toString());
                output = errorMsg.toString();
            }
        }
        System.out.println(flag);
  /*      //System.out.println(bookInfo);

        //input hardcodat care merge
        //JSONObject bookInfo = new JSONObject("{\"ISBN\": \"978-606-623-2\", \"author\": \"JRR Tolkien\", \"message\": \"decoded\", \"success\": \"DA\", \"title\": \"Silmarilion\"}");

//Caz 1: eroare la beleuz
        //JSONObject bookInfo = new JSONObject("{\"ISBN\": \"978-0135048740\", \"author\": \"JRR Tolkien\", \"message\": \"decoded\", \"success\": \"DA\", \"title\": \"Silmarilion\"}");
//Caz 2: should work
        //JSONObject bookInfo = new JSONObject("{\"ISBN\": \"978-006-092-043-2\", \"author\": \"Andy Mitchell\", \"message\": \"decoded\", \"success\": \"DA\", \"title\": \"\"}");


        DBClient client = new DBClient();
        try {
            DBClient.dod();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("bookInfo"+bookInfo);
       try {
            dbReviewsResponse = DatabaseCU.databaseRequestReviews(bookInfo);
            //System.out.println("aici");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        JSONObject jsonObjectForMA = null;
        boolean foundReviewError = false;
        String bookISBN = null;
        String errorMessage = null;
        System.out.println("bookInfo: " + bookInfo);
        System.out.println("REVIEWS DIN DATABASE : " + dbReviewsResponse);
        if (flag) {
            System.out.println("aici2");
            // TODO Decomenteaza linia urmatoare pentru a testa crawlerul
            dbReviewsResponse = null;
            if ((dbReviewsResponse == null) || dbReviewsResponse.toString().equalsIgnoreCase("{}")) {
                try {
                    bookReviews = ReviewCollectorCU.requestReviews(bookInfo);
                    System.out.println("bookReviews" + bookReviews.toString());
                    if (bookReviews.has("mesajEroare")) {
                        foundReviewError = bookReviews.get("mesajEroare").toString().contains("review-uri");
                        System.out.println(foundReviewError);
                        if (foundReviewError == true) {
                            System.out.println("aici");
                            isbnFound = false;
                            flag = false;
                            bookISBN = bookInfo.get("ISBN").toString();
                            bookISBN = bookISBN.replaceAll("[^0-9]", "");
                            System.out.println(bookISBN);
                            jsonObjectForMA =new JSONObject();
                            jsonObjectForMA.put("ISBN", bookISBN);
                            output = jsonObjectForMA.toString();
                        } else {
                            flag = false;
                            errorMsg = new JSONObject(bookInfo.toString());
                            output = errorMsg.toString();
                        }
                    }
                    System.out.println("REVIEWS DE LA CRAWLER : " + bookReviews.toString());

                    //TODO  DatabaseCU.sendReviews(bookReviews);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                bookReviews = dbReviewsResponse;
            }
            if (isbnFound == true) {
                output = bookReviews.toString();
            } else {
                output = jsonObjectForMA.toString();
            }
        } else {
            output = errorMsg.toString();
        }
        System.out.println(output);
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
