import java.io.*;

public class Request {
    void run(){
        ProcessBuilder processBuilder = new ProcessBuilder();
//      al treilea argument al functiei command este de forma "curl -H \"content-Type:application/json\" -d jsonFilePath URL\n"
        processBuilder.command("bash", "-c", "curl -H \"Content-Type:application/json\" -d @/home/s/IdeaProjects/JavaClientCorrect/jsonFile.json https://szmuschi.pythonanywhere.com/api\n");
        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Request was successfull!\n");
                System.out.println(output);
                System.exit(0);
            } else {
                System.out.println("Abnormal behavior in request!");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
