package DAO;

import dbObjects.Review;
import serverConnection.Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ReviewDAO {
    private Connection connection;
    
    public ReviewDAO() {}
    
    public ReviewDAO(Connection connection) {
        this.connection = connection;

    }
    
    public ArrayList<Review> getReviewsByBookID(int bookID) throws IOException {
        String sql = "SELECT * FROM recenzii WHERE ID_CARTE = ?";
        ArrayList<Review> reviewsList = new ArrayList<Review>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, bookID);     
            ResultSet rs = stmt.executeQuery();
            
            while (true == rs.next()) {
                reviewsList.add(new Review(rs.getDouble(1), rs.getDouble(2), rs.getByte(3), rs.getString(4)));
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
            return null;
        }
        return reviewsList;
    }
    
    public ArrayList<Review> getReviewsByBookTitle(String bookTitle) {
        String sql = "select r.review from recenzii r JOIN carte c on r.id_carte=c.id where c.titlu=?;";
        ArrayList<Review> reviewsList = new ArrayList<Review>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, bookTitle);     
            ResultSet rs = stmt.executeQuery();
            
            while (true == rs.next()) {
                reviewsList.add(new Review(rs.getDouble(1), rs.getDouble(2), rs.getByte(3), rs.getString(4)));
            }   
        } catch (SQLException e) {
            //Display error message
            File file = new File("messages.txt");
            //Create the file
            try {
                file.createNewFile();
                String message = e.getMessage();

                BufferedWriter writer2 = new BufferedWriter(
                        new FileWriter("messages.txt", true)
                );
                writer2.write(message + "\n");
                writer2.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;
        }
        return reviewsList;
    }
    
    public Review getLatestReviewByBookID(int bookID) throws IOException {
        String sql = "SELECT * FROM recenzii WHERE ID_CARTE = ? ORDER BY ID DESC";
        Review review = null;
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, bookID);     
            ResultSet rs = stmt.executeQuery();
            
            if (true == rs.next()) {
                review = new Review(rs.getInt(1), rs.getInt(2), rs.getByte(3) , rs.getString(4));
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
            return null;
        }
        
        return review;
    }

    public void addReview(double bookId, byte rating, String reviewText) throws IOException {
        String sqlFindMaxID = "SELECT max(id) FROM recenzii";
        int maxID = -1;
        
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlFindMaxID);

            if (true == rs.next()) {
                maxID = rs.getInt(1);
                System.out.println("ID MAXIM : " + maxID);
            }
            maxID ++;
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
        
        String sql = "INSERT INTO recenzii VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setInt(1, maxID);
            prepStmt.setDouble(2, bookId);
            prepStmt.setByte(3, rating);
            prepStmt.setString(4, reviewText);
            
            prepStmt.execute();
            //Display message
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = "Rewiew added by user with id: " + maxID;

            BufferedWriter writer2 = new BufferedWriter(
                    new FileWriter("messages.txt", true)
            );
            writer2.write(message + "\n");
            writer2.close();
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
    
    public double getAverageRatingByBookId(int bookID) throws IOException {
        String sql = "SELECT avg(rating) FROM recenzii where id_carte = ?";
        double ratingAverage = 0;
    
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, bookID);     
            ResultSet rs = stmt.executeQuery();
            
            if (true == rs.next()) {
                ratingAverage = rs.getByte(1);
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
        
        return ratingAverage;
    }
    
    public void deleteReviewByID(double id) throws IOException {
        String sql = "DELETE FROM recenzii WHERE id = ?";
        
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setDouble(1, id);
            
            prepStmt.executeQuery();
            //Display message
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = "Review deleted, id: " + id;

            BufferedWriter writer2 = new BufferedWriter(
                    new FileWriter("messages.txt", true)
            );
            writer2.write(message + "\n");
            writer2.close();
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
