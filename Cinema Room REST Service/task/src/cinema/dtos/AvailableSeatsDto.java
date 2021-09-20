package cinema.dtos;

import cinema.model.Cinema;

import java.util.List;

public class AvailableSeatsDto {
    private final int totalRows;
    private final int totalColumns;
    private final List<SeatDto> availableSeats;
    public AvailableSeatsDto(Cinema cinema) {
        this.totalRows = cinema.getTotalRows();
        this.totalColumns = cinema.getTotalColumns();
        availableSeats = cinema.getAvailableSeatDtos();
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public List<SeatDto> getAvailableSeats() {
        return availableSeats;
    }
}
