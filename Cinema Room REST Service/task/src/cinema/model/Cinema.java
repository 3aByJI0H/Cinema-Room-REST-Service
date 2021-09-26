package cinema.model;

import cinema.dtos.CinemaStats;
import cinema.dtos.PurchaseResponse;
import cinema.dtos.SeatDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Cinema {
    private final CinemaHall cinemaHall;
    private final Map<UUID, Seat> tokenToSeat = new ConcurrentHashMap<>();

    public Cinema(@Value("9") int totalRows, @Value("9") int totalColumns) {
        cinemaHall = new CinemaHall(totalRows, totalColumns);
    }

    public List<SeatDto> getAvailableSeatDtos() {
        List<SeatDto> availableSeats = new ArrayList<>();
        cinemaHall.forEach(
                (row, col) -> availableSeats.add( makeSeatDto( cinemaHall.getSeat(row, col) ) )
        );
        return availableSeats;
    }

    public boolean isSeatAvailable(int row, int col) {
        return cinemaHall.getSeat(row, col).isAvailable();
    }

    public int getTotalRows() {
        return cinemaHall.getTotalRows();
    }

    public int getTotalColumns() {
        return cinemaHall.getTotalColumns();
    }
    public int getTotalSeats() {
        return getTotalRows() * getTotalColumns();
    }

    public PurchaseResponse purchase(int row, int column) {
        Seat seat = cinemaHall.getSeat(row, column);
        boolean isSuccessful = seat.setAvailable(false);
        if (!isSuccessful)
            return null;

        UUID newToken = UUID.randomUUID();
        tokenToSeat.put(newToken, seat);
        return new PurchaseResponse( newToken, makeSeatDto(seat) );
    }

    public boolean validate(int row, int col) {
        boolean isRowValid = 1 <= row && row <= getTotalRows();
        boolean isColumnValid = 1 <= col && col <= getTotalColumns();
        return  isRowValid && isColumnValid;
    }

    public SeatDto getSeatDto(int row, int column) {
        return makeSeatDto(cinemaHall.getSeat(row, column));
    }
    public SeatDto getSeatDtoByToken(UUID token) {
        return makeSeatDto(tokenToSeat.get(token));
    }

    public SeatDto makeSeatDto(Seat seat) {
        if (seat == null)
            return null;

        SeatDto seatDto = new SeatDto();
        seatDto.setRow(seat.getRow());
        seatDto.setColumn(seat.getColumn());
        seatDto.setPrice(seat.getPrice());
        return seatDto;
    }

    public SeatDto returnTicket(UUID token) {
        Seat seat = tokenToSeat.remove(token);
        if (seat == null) {
            return null;
        }
        seat.setAvailable(true);
        return makeSeatDto(seat);
    }
    private final String statPassword = "super_secret";
    public CinemaStats getStatistics(String password) {
        if ( !statPassword.equals(password) )
            return null;

        int numberOfPurchasedTickets = tokenToSeat.size();
        int numberOfAvailableSeats = getTotalSeats() - numberOfPurchasedTickets;
        return new CinemaStats(
                calculateCurrentIncome(),
                numberOfAvailableSeats,
                numberOfPurchasedTickets
        );
    }

    private int calculateCurrentIncome() {
        int income = 0;
        for (Seat seat : tokenToSeat.values()) {
            income += seat.getPrice();
        }
        return income;
    }
}
