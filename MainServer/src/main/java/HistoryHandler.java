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

            if (!json.has("method") || !json.has("username")) {
                httpExchange.sendResponseHeaders(400, 0);
                outputStream.close();
                return;
            }
            DatabaseCU.requestHistory(json);
            int responseCode = (int) json.get("responseCode");
            httpExchange.sendResponseHeaders(responseCode, 1);
            if (responseCode == 200) {
                outputStream.write("1".getBytes());
            }
            else outputStream.write("0".getBytes());
            outputStream.close();
        }else if("GET".equals(httpExchange.getRequestMethod())){
            System.out.println("Not Post!");
            httpExchange.sendResponseHeaders(404, 8);
            httpExchange.getResponseBody().write("Not a POST request!".getBytes());
            httpExchange.getResponseBody().close();
        }
    }
}
