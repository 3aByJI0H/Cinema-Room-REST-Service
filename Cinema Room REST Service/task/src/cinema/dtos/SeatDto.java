package cinema.dtos;

public class SeatDto {
    private Integer row;
    private Integer column;
    private Integer price;

    public void setRow(Integer row) {
        this.row = row;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getPrice() {
        return price;
    }

    public SeatDto() {

    }
}
