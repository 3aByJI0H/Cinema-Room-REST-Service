package cinema.model;

class Seat {
    private final int price;
    private final int row;
    private final int column;
    private boolean available = true;

    boolean isAvailable() {
        return available;
    }

    int getPrice() {
        return price;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = (row <= 4) ? 10 : 8;
    }

    synchronized boolean setAvailable(boolean value) {
        boolean isSuccessful = available != value;
        if (isSuccessful)
            available = value;
        return isSuccessful;
    }
}
