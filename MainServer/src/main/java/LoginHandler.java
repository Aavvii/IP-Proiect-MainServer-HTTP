import CommunicationUnits.DatabaseCU;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;


import java.io.*;
import java.nio.file.Files;

public class LoginHandler implements HttpHandler {
    static int id = 0;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        JSONObject requestParamValue=null;
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

             if (!json.has("username") || !json.has("password")) {
                 httpExchange.sendResponseHeaders(400, 0);
                 outputStream.close();
                 return;
             }
             DatabaseCU.requestLogin(json);
             int responseCode = (int) json.get("responseCode");

             httpExchange.sendResponseHeaders(responseCode, 1);

             if (responseCode == 200) {
                 outputStream.write("1".getBytes());
             }
             else outputStream.write("0".getBytes());
//             outputStream.flush();
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