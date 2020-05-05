package DAO;

import dbObjects.Book;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public Book getBookByTitle(String title) throws IOException {
        Book newBook = new Book();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("select * from carte where titlu = '" + title + "' ");
            while (rs.next()) {
                newBook.setId(rs.getInt(1)); //id
                newBook.setTitle(rs.getString(2));//titlu
                newBook.setAuthor(rs.getString(3));//autor
                newBook.setGenre(rs.getString(4));//gen
                newBook.setPageNr(rs.getInt(5));//nr_pag
                newBook.setIsbn(rs.getString(6));//isbn
                newBook.setAvgRating(rs.getByte(7));//raiting
                newBook.setResume(rs.getString(8));//rezumat
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
        return newBook;
    }

    public Book getBookByISBN(String isbn) throws IOException {
        Book newBook = new Book();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("select * from carte where isbn = '" + isbn + "' ");
            while (rs.next()) {
                newBook.setId(rs.getDouble(1)); //id
                newBook.setTitle(rs.getString(2));//titlu
                newBook.setAuthor(rs.getString(3));//autor
                newBook.setGenre(rs.getString(4));//gen
                newBook.setPageNr(rs.getInt(5));//nr_pag
                newBook.setIsbn(rs.getString(6));//isbn
                newBook.setAvgRating(rs.getDouble(7));//raiting
                newBook.setResume(rs.getString(8));//rezumat
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
        return newBook;
    }

    public ArrayList<Book> getBooksByAuthor(String author) throws IOException {
        ArrayList<Book> list = new ArrayList<Book>();
        Book newBook = new Book();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from carte where autor = '" + author + "' ");
            while (rs.next()) {
                list.add(new Book(rs.getDouble(1),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getByte(7),
                        rs.getString(8)
                ));
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

    public ArrayList<Book> getBooksByGenre(String genre) throws IOException {
        ArrayList<Book> list = new ArrayList<Book>();
        Book newBook = new Book();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from carte where gen = '" + genre + "' ");
            while (rs.next()) {
                list.add(new Book(rs.getDouble(1),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getByte(7),
                        rs.getString(8)
                ));
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

    public void deleteBookByISBN(String isbn) throws IOException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            int id = -1;
            //sterg din istoric si recenzii mai intai
            ResultSet rs = stmt.executeQuery("select id from carte where isbn = '" + isbn + "' ");
            while (rs.next()) {
                id = rs.getInt(1); //id
            }

            String sql1 = "delete from istoric where id_carte =" + id;
            stmt.executeUpdate(sql1);

            String sql2 = "delete from recenzii where id_carte =" + id;
            stmt.executeUpdate(sql2);

            String sql = "delete from carte where isbn ='" + isbn + "' ";
            stmt.executeUpdate(sql);
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = "Book with isbn : " + isbn + " was deleted.";

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

    public void addBook(String title, String author, String genre, int pageNr, String isbn, double rating, String resume) throws IOException {
        String sqlFindMaxID = "SELECT max(id) FROM carte";
        int maxID = -1;

        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs = stmt1.executeQuery(sqlFindMaxID);

            if (true == rs.next()) {
                maxID = rs.getInt(1);
            }
            maxID ++;


            String query = " insert into carte (id, titlu, autor, gen, nr_pag, isbn, rating, rezumat)"
                    + " values (" + maxID + ", '" + title + "','" + author + "', '" + genre + "'," + pageNr + ", '" + isbn + "'," + rating + ", '" + resume + "')";
            Statement stmt = connection.createStatement();
            stmt.execute(query);
            File file = new File("messages.txt");
            //Create the file
            file.createNewFile();
            String message = "A new book was added: " + title;

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
}

