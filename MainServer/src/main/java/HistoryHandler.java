import CommunicationUnits.DatabaseCU;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class HistoryHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String historyString = null;
        if("POST".equals(httpExchange.getRequestMethod())) {
            BufferedReader postInfo = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            StringBuilder requestContents = new StringBuilder();
            String aux = "";
            try {
                while ((aux = postInfo.readLine()) != null) {
                    requestContents.append(aux);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject json = new JSONObject(requestContents.toString());
            OutputStream outputStream = httpExchange.getResponseBody();

                //json:{"username":"maria123"} -- de la mobile app
            if (!json.has("username")) {
                httpExchange.sendResponseHeaders(400, 0);
                outputStream.close();
                return;
            }
            System.out.println("request History from DB");
            historyString= DatabaseCU.requestHistory(json);
            System.out.println(historyString);
            //sendResponse to mobileApp

            httpExchange.sendResponseHeaders(200, historyString.length());
            outputStream.write(historyString.getBytes());
            //outputStream.flush();
            outputStream.close();

        }else if("GET".equals(httpExchange.getRequestMethod())){
            System.out.println("Not Post!");
            httpExchange.sendResponseHeaders(404, 8);
            httpExchange.getResponseBody().write("Not a POST request!".getBytes());
            httpExchange.getResponseBody().close();
        }
    }
}
