package DAO;

import dbObjects.Book;
import dbObjects.History;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


public class HistoryDAO {
    private Connection connection;
    public HistoryDAO(Connection connection) {
        this.connection = connection;
    }


    public ArrayList<History> getHistoryByUserId(double userId) throws IOException {
        ArrayList<History> list = new ArrayList<History>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("select * from istoric where id_utilizator = " + userId);
        while (rs.next()) {

            list.add(new History(rs.getInt(1), rs.getDouble(2), rs.getString(3)));
        }
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
        return list;
    }

    public void deleteHistoryByUserId(double userId) throws IOException { // toate inregistrarile care au acel user id
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        String sql1 = "delete from istoric where id_utilizator =" + userId;
        stmt.executeUpdate(sql1);
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



    public void addHistory(double userId, double visitedBookId, String visitationDate) throws IOException {
        try {
            String query = "insert into istoric (id_utilizator, id_carte, data_vizitare)"
                    + " values (" + userId + ", " + visitedBookId + "," + "TO_DATE('" + visitationDate + "','MM.DD.YYYY'))";
            Statement stmt = connection.createStatement();

            stmt.execute(query);
            //display message
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = "A new book was added to history for UserId : "+ userId + " book with id: " +visitedBookId;

            BufferedWriter writer2 = new BufferedWriter(
                    new FileWriter("messages.txt", true)
            );
            writer2.write(message + "\n");
            writer2.close();
        } catch (Exception e)
        {
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
