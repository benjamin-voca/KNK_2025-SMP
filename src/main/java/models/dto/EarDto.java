package models.dto;

public class EarDto {

    private int id;
    private int acceptedId;
    private int availableSeats;
    private boolean full;


    public EarDto(int id, int acceptedId, int availableSeats, boolean full){
        this.id = id;
        this.acceptedId = acceptedId;
        this.availableSeats = availableSeats;
        this.full = full;
    }

    public void setAvailableSeats(int availableSeats){
        this.availableSeats = availableSeats;
    }

    public void setFull(boolean full){
        this.full = full;
    }

    public int getId() {
        return id;
    }

    public int getAcceptedId() {
        return acceptedId;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean isFull() {
        return full;
    }

}