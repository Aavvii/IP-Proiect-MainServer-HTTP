package dbObjects;

public class Book {
    private double id;
    private String title;
    private String author;
    private String genre;
    private int pageNr;
    private String isbn;
    private double avgRating;
    private String resume;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Book(double id, String author, String title, String genre, int pageNr, String isbn, double avgRating, String resume) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.pageNr = pageNr;
        this.isbn = isbn;
        this.avgRating = avgRating;
        this.resume = resume;
    }

    public Book() {
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPageNr() {
        return pageNr;
    }

    public void setPageNr(int pageNr) {
        this.pageNr = pageNr;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
