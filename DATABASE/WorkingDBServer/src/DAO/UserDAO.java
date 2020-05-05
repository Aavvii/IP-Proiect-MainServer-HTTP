/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import dbObjects.User;
import serverConnection.Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class UserDAO {
    private Connection connection;
    
    public UserDAO() {}
    
    public UserDAO(Connection connection) {
        this.connection = connection;
    }
    
    public User getUserByNickname(String nickname) throws IOException {
        String sql = "SELECT * FROM utilizator WHERE nickname = ?";
        User user = null;
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nickname);     
            ResultSet rs = stmt.executeQuery();
            
            if (true == rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
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
        
        return user;
    }

    public void addUser(String nume, String prenume, String nickname, String password, String email) throws IOException {
        String sqlFindMaxID = "SELECT max(id) FROM utilizator";
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

        String sql = "INSERT INTO utilizator VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setInt(1, maxID);
            prepStmt.setString(2, nume);
            prepStmt.setString(3, prenume);
            prepStmt.setString(4, nickname);
            prepStmt.setString(5, password);
            prepStmt.setString(6, email);
            prepStmt.execute();
            //Display message
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = "A new user added: " + nume + " " + prenume;

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

    public void updateUserPassword(String nickname, String newPassword) throws IOException {
        String sql = "UPDATE utilizator SET parola = ? WHERE nickname = ?";
        
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, newPassword);
            prepStmt.setString(2, nickname);
            prepStmt.executeQuery();
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

    public void deleteUserByFirstName(String nume) throws IOException {
        String sql = "DELETE FROM utilizator WHERE nume = ?";
        
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, nume);
            prepStmt.executeQuery();
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
    
    public void deleteUserByLastName(String prenume) throws IOException {
        String sql = "DELETE FROM utilizator WHERE prenume = ?";
        
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, prenume);
            prepStmt.executeQuery();
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
    
    public void deleteUserByNickname(String nickname) throws IOException {
        String sql = "DELETE FROM utilizator WHERE nickname = '" + nickname + "'";
        Statement prepStmt = null;
        try {
            prepStmt = connection.createStatement();
            prepStmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = "user deleted by nickname: " + nickname;

            BufferedWriter writer2 = new BufferedWriter(
                    new FileWriter("messages.txt", true)
            );
            writer2.write(message + "\n");
            writer2.close();
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
   
    public void deleteUserByEmail(String email) throws IOException {
        String sql = "DELETE FROM utilizator WHERE email = ?";
        
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, email);     
            prepStmt.execute();
            //Display error message
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = "User deleted by email: " + email;

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
