package cinema.model;

public class Seat {
    private final int price;
    private final int row;
    private final int column;
    private boolean available = true;

    public boolean isAvailable() {
        return available;
    }

    public int getPrice() {
        return price;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = (row <= 4) ? 10 : 8;
    }

    public synchronized boolean setAvailable(boolean value) {
        boolean isSuccessful = available != value;
        if (isSuccessful)
            available = value;
        return isSuccessful;
    }
}
