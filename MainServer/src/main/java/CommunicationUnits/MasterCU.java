package CommunicationUnits;

import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Componenta ce va face legatura intre toate celelalte Communication Units
 * Aici vor va fi manipulata informatia primita de la si trimisa la celelalte componente are aplicatiei.
 * Vor fi apelate functii din celelate CU-uri.
 * In momentul actual codul este de la partea de socket-uri - si nu este util.
 */

public class MasterCU {

    private String input;
    private String output;

    public int prepareResponse() {

        JSONObject inputJSON = new JSONObject(input);

        ImageProcessorCU ImageProcessor = new ImageProcessorCU();
        JSONObject bookInfo = ImageProcessor.ImageProcessorCommunication(inputJSON);



        return 0;
    }

    public MasterCU(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
    /*public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(6969);
        DatabaseCU databaseCU=new DatabaseCU();
        JSONObject obj = new JSONObject();
        obj.put("name", "foo");
        obj.put("num", new Integer(100));
        obj.put("balance", new Double(1000.21));
        obj.put("is_vip", new Boolean(true));
        System.out.println(databaseCU.databaseCommunication(obj).toString());
        while(true) {
            Socket client = null;
            try {
                client = server.accept();

                System.out.println("Client connected");

                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                Thread newThread = new ProcessManagingUnit(client, in, out);

                newThread.start();
            }
            catch (Exception e) {
                client.close();
                e.printStackTrace();
            };
        }
    }*/
}