package cinema.dtos;

import java.util.UUID;

public class PurchaseResponse {
    private final String token;
    private final SeatDto ticket;

    public String getToken() {
        return token;
    }

    public SeatDto getTicket() {
        return ticket;
    }

    public PurchaseResponse(UUID token, SeatDto ticket) {
        this.token = token.toString();
        this.ticket = ticket;
    }
}
