package CommunicationUnits;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MobileAppCU implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String requestParamValue=null;
        if("GET".equals(httpExchange.getRequestMethod())) {
            System.out.println("GET!");
            System.out.println(httpExchange.getRequestURI());
            requestParamValue = handleGetRequest(httpExchange);
        }else if("POST".equals(httpExchange.getRequestMethod())) {
            System.out.println("Post!");
            requestParamValue = handlePostRequest(httpExchange);
        }
        else
        {
            System.out.println("not Post not Get!");
        }
        handleResponse(httpExchange,requestParamValue);
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        BufferedReader postInfo = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
        System.out.println(httpExchange.getRequestHeaders().getFirst("Content-type"));
        StringBuilder ret = new StringBuilder();
        String aux = "test: ";
        try {
            while ((aux = postInfo.readLine()) != null) {
                ret.append(aux);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.
        getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {

        OutputStream outputStream = httpExchange.getResponseBody();
        // encode HTML content
        MasterCU processResponse = new MasterCU(requestParamValue);
        processResponse.prepareResponse();
        String response = processResponse.getOutput();
        // this line is a must
        httpExchange.sendResponseHeaders(200, response.length());
        //in loc de htmlResponse va fi un String/JSON reprezentand raspunsul
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}