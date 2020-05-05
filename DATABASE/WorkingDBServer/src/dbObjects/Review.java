package dbObjects;

public class Review {
    private double id;
    private double bookId;
    private byte rating;
    private String reviewText;

    public Review(double id, double bookId, byte rating, String reviewText) {
        this.id = id;
        this.bookId = bookId;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public Review() {
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getBookId() {
        return bookId;
    }

    public void setBookId(double bookId) {
        this.bookId = bookId;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
