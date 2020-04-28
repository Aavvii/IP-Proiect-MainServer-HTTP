import java.io.*;
import java.util.Base64;
import org.json.simple.*;


public class Main
{
    public static void main(String[] args) throws Exception{
        try {
            File f = new File("./src/images/images.jpg"); // the image send
            try {
                String encodstring = encodeFileToBase64Binary(f); // the base64 encoding
                System.out.println("Successfully encoded image to base64!");

                JSONObject obj = new JSONObject();
                JSONObject var = new JSONObject();
                var.put("base64",encodstring);
                obj.put("test", var);
                FileWriter file = null;
                try {

                    // Prints JSON into file
                    file = new FileWriter("/home/s/IdeaProjects/JavaClientCorrect/jsonFile.json");
                    file.write(obj.toJSONString());
                    System.out.println("Successfully Copied JSON Object to File...");

                } catch (IOException e) {
                    e.printStackTrace();

                } finally {

                    try {
                        file.flush();
                        file.close();

//                      making the post request and printing the result
                        Request request = new Request();
                        request.run();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error in base64 encoding : " + e);
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.getEncoder().encodeToString(bytes));
    }
}