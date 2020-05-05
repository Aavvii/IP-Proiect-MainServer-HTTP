package serverConnection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Database {
    private static Database databaseInstance = null;
    public static Connection connection;

    private Database(String url, String user, String password) throws IOException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
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


    public static Database getDatabaseInstance(String url, String user, String password) throws IOException {
        if (databaseInstance == null) {
            databaseInstance = new Database(url, user, password);
        }
        return databaseInstance;
    }

    public static void closeDatabase() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
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
