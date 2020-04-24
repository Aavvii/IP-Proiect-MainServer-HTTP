import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 6789), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        server.createContext("/test", new MyHttpHandler());
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println(" Server started on port 8001");
    }
}
