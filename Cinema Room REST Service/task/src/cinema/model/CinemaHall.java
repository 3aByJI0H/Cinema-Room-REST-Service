package cinema.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class CinemaHall {
    private final int totalRows;
    private final int totalColumns;
    private final List<Seat> seats;

    public CinemaHall(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.seats = initSeats(totalRows * totalColumns);
    }

    private List<Seat> initSeats(int totalSeats) {
        List<Seat> newSeats = new ArrayList<>(totalSeats);
        forEach( (row, col) -> newSeats.add( new Seat(row, col) ) );
        return newSeats;
    }
    public void forEach(BiConsumer<Integer, Integer> consumer) {
        for (int row = 1; row <= totalRows; row++) {
            for (int col = 1; col <= totalColumns; col++) {
                consumer.accept(row, col);
            }
        }
    }
    private int flattenIndex(int row, int column) {
        return (row-1) * totalColumns + (column-1);
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public Seat getSeat(int row, int col) {
        return seats.get( flattenIndex(row, col) );
    }
}
