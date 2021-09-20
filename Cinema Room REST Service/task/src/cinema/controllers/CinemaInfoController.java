package cinema.controllers;

import cinema.dtos.*;
import cinema.model.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaInfoController {
    @Autowired
    private Cinema cinema;

    @GetMapping("/seats")
    public AvailableSeatsDto getAvailableSeats() {
        return new AvailableSeatsDto(cinema);
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody SeatCoordinatesDto seat) {
        int row = seat.getRow(), column = seat.getColumn();
        if ( !cinema.validate(row, column) )
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The number of a row or a column is out of bounds!"
            );
        PurchaseResponse purchaseResponse = cinema.purchase(row, column);
        if ( purchaseResponse == null )
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The ticket has been already purchased!"
            );

        return purchaseResponse;
    }

    @PostMapping("/return")
    public Map<String, SeatDto> returnTicket(@RequestBody Map<String, String> kwargs) {
        final String stringToken = kwargs.get("token");
        if (kwargs.size() != 1 || stringToken == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Wrong request format!"
            );

        System.out.println(stringToken);
        UUID token = UUID.fromString(stringToken);
        SeatDto returnTicketSeatDto = cinema.returnTicket(token);
        if (returnTicketSeatDto == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Wrong token!"
            );

        return Map.of("returned_ticket", returnTicketSeatDto);
    }

    @PostMapping("/stats")
    public CinemaStats getStatistics(@RequestParam(required = false) String password) {
        CinemaStats stats =  cinema.getStatistics(password);
        if (stats == null)
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "The password is wrong!"
            );

        return stats;
    }
}
