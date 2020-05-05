package serverConnection;

import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
//imports
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

public class Server {
    public static void main(String[] args) throws IOException {
        try {
            //pay attention to fullPath, you change to '../config.json'
            String fullPath = "src\\config.json";
            String jsonContents = new String((Files.readAllBytes(Paths.get(fullPath))));
            JSONObject obj = new JSONObject(jsonContents);
            
            String address = (String) obj.get("address");
            int port = (int)obj.get("port");
            String root = (String)obj.get("path");
            
            HttpServer server = HttpServer.create(new InetSocketAddress(address, port), 0);
            server.createContext(root, new MyHttpHandler());
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            //Display message
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = "Server listening at port " + port;

            BufferedWriter writer2 = new BufferedWriter(
                    new FileWriter("messages.txt", true)
            );
            writer2.write(message + "\n");
            writer2.close();
        } catch (IOException e) {
            //Display error message
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = e.getMessage();

            BufferedWriter writer2 = new BufferedWriter(
                    new FileWriter("messages.txt", true)
            );
            writer2.write(message + "\n");
            writer2.close();
        }
    }

}
