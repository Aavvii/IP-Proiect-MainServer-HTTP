package CommunicationUnits;

import org.json.JSONObject;

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

    public static JSONObject requestBookInfo(JSONObject imagineJson) {
        JSONObject jsonResponse = null;
        URL obj = new URL("https://szmuschi.pythonanywhere.com/api");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("content-type", "application/json");
        postConnection.setDoOutput(true);
        BufferedWriter send = new BufferedWriter(new OutputStreamWriter(postConnection.getOutputStream()));
        send.write(imagineJson.toString());
        send.close();

        int responseCode = postConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            jsonResponse = new JSONObject(response);
        }
        return jsonResponse;
    }

}
