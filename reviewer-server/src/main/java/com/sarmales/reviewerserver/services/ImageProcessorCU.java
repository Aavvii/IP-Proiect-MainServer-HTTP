package com.sarmales.reviewerserver.services;

import com.sarmales.reviewerserver.handler.ErrorHandling;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ImageProcessorCU este responsabil pentru comunicarea cu componenta Image Processor
 * Se ocupa de primirea si trimiterea de JSON-uri
 * JSON-ul trimis reprezinta o poza
 * JSON-ul primit reprezinta informatie despre carte: Titlu, Autor, etc
 */
public class ImageProcessorCU {
//trimitem imaginea catre imageProcessorCU(e parametrul functiei-adica imaginea de la mobile app)
    //si primim titlul si autorul si l returnam ca json object pentru a fi folosit ulterior
    /*
    * {"ISBN": "978-606-623-2", "author": "JRR Tolkien", "message": "decoded",
    * "success": "ok", "title": "Silmarilion"}
    */

    public static JSONObject requestBookInfo(String imagineJson) {
        JSONObject jsonResponse = null;
        URL obj = null;
        try {
            obj = new URL("https://szmuschi.pythonanywhere.com/api");
            HttpURLConnection postConnection = null;

            postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("content-type", "application/json");

            postConnection.setDoOutput(true);
            BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
            // TODO send.write(imagineJson.toString());
            send.write(imagineJson.toString());
            send.close();

            int responseCode = 0;
            responseCode = postConnection.getResponseCode();
            System.out.println("IMGPROC CU response code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inputLine = null;
                StringBuffer response = new StringBuffer();
                while (true) {
                    if (!((inputLine = in.readLine()) != null)) break;
                    response.append(inputLine);
                }
                System.out.println("ImageProcesorCU response:" + response.toString());
                in.close();
                if(response.toString().equals("")) {
                    jsonResponse =new JSONObject();
                    jsonResponse.put("mesajEroare","Eroare interna");
                    jsonResponse.put("responseCode","406");
                }
                else {
                    //JSONObject newJson = new JSONObject(response.toString());
                    if (ErrorHandling.isValid(response.toString())) {
                        jsonResponse = new JSONObject(response.toString());
                        //jsonResponse.put("method", "getBookByISBN");
                        //jsonResponse.put("argument", newJson.get("ISBN"));
                        //System.out.println(jsonResponse.toString());
                        // TODO Linia asta va fi stearsa dupa ce raspunsul de la Image Processor nu va mai fi default
//                        jsonResponse.put("ISBN", "978-0135048740");
                        if (jsonResponse.has("success")) {
                            if (jsonResponse.get("success").equals("NU")) {
                                if (jsonResponse.has("message")) {
                                    if (jsonResponse.get("message").equals("Poza neclara!Nu s-a putut identifica isbn-ul!")) {
                                        jsonResponse = new JSONObject();
                                        jsonResponse.put("mesajEroare", "Poza neclara");
                                        jsonResponse.put("responseCode", "401");
                                    }else {
                                        jsonResponse = new JSONObject();
                                        jsonResponse.put("mesajEroare", "Eroare interna");
                                        jsonResponse.put("responseCode", "406");
                                    }
                                }
                            } else {
                                if (ErrorHandling.isJsonEmpty(jsonResponse, "ISBN") || !jsonResponse.has("ISBN")) {
                                    jsonResponse = new JSONObject();
                                    jsonResponse.put("mesajEroare", "Eroare interna");
                                    jsonResponse.put("responseCode", "406");
                                }
                            }
                        }else{
                            jsonResponse = new JSONObject();
                            jsonResponse.put("mesajEroare", "Eroare interna");
                            jsonResponse.put("responseCode", "406");
                        }
                    }
                }
            }else{
                jsonResponse =new JSONObject();
                jsonResponse.put("mesajEroare","Eroare interna");
                jsonResponse.put("responseCode","406");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(jsonResponse);
        return jsonResponse;
    }

}
