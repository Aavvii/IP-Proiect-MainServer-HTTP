import CommunicationUnits.DatabaseCU;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class SignUpHandler implements HttpHandler {
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

            if (!json.has("name") || !json.has("lastName") || !json.has("nickname") || !json.has("password") || !json.has("email")) {
                httpExchange.sendResponseHeaders(400, 0);
                outputStream.close();
                return;
            }

            if (DatabaseCU.singUpToDatabase(json))
            {
                httpExchange.sendResponseHeaders(200, 1);
                outputStream.write("1".getBytes());
            }

            else {
                httpExchange.sendResponseHeaders(406, 1);
                outputStream.write("0".getBytes());
            }
            outputStream.close();
        }
        else
        {
            System.out.println("not Post!");
            httpExchange.sendResponseHeaders(404, 8);
            httpExchange.getResponseBody().write("Sal cf?".getBytes());
            httpExchange.getResponseBody().close();
        }
    }
}
