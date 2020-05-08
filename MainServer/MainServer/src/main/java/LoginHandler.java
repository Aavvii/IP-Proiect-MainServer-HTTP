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
            System.out.println("Post!");
            requestParamValue = handlePostRequest(httpExchange);
        }
        else
        {
            System.out.println("not Post not get!");
        }
        handleResponse(httpExchange,requestParamValue);
    }

    private JSONObject handlePostRequest(HttpExchange httpExchange) {


        BufferedReader postInfo = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
        
        StringBuilder ret = new StringBuilder();
        String aux = "test: ";
        try {
            while ((aux = postInfo.readLine()) != null) {
                ret.append(aux);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject(ret.toString());
        System.out.println(json.toString() + "log in");

//        System.out.println(ret.toString());
        return json;
    }

    private void handleResponse(HttpExchange httpExchange, JSONObject requestParamValue)  throws  IOException {

        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<html>").
                append("<body>").
                append("<h1>")
                .append(requestParamValue)
                .append("</h1>")
                .append("</body>")
                .append("</html>");
        // encode HTML content
        String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());

        // this line is a must
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}