package CommunicationUnits;

import org.json.JSONObject;

/**
 * ImageProcessorCU este responsabil pentru comunicarea cu componenta Image Processor
 * Se ocupa de primirea si trimiterea de JSON-uri
 * JSON-ul trimis reprezinta o poza
 * JSON-ul primit reprezinta informatie despre carte: Titlu, Autor, etc
 */
public class ImageProcessorCU {

    public JSONObject ImageProcessorCommunication(JSONObject imagineJson) {
        return null;
    }
    //trimitem imaginea catre imageProcessorCU(e parametrul functiei-adica imaginea de la mobile app)
    //si primim titlul si autorul si l returnam ca json object pentru a fi folosit ulterior

}