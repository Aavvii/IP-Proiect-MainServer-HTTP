package dbObjects;

public class History {
    private double id;
    private double visitedBookId;
    private String visitationDate;

    public History(double id, double visitedBookId, String visitationDate) {
        this.id = id;
        this.visitedBookId = visitedBookId;
        this.visitationDate = visitationDate;
    }

    public History() {
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public Double getVisitedBookId() {
        return visitedBookId;
    }

    public void setVisitedBook(Double visitedBookId) {
        this.visitedBookId = visitedBookId;
    }

    public String getVisitationDate() {
        return visitationDate;
    }

    public void setVisitationDate(String visitationDate) {
        this.visitationDate = visitationDate;
    }
}
