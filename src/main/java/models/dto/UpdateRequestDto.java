package models.dto;



public class UpdateRequestDto {
    private int id;
    private int student_id;
    private boolean accepted;
    private boolean repeat;

    public UpdateRequestDto(int id, int student_id, boolean accepted, boolean repeat) {
        this.id = id;
        this.student_id = student_id;
        this.accepted = accepted;
        this.repeat = repeat;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public int getId() {
        return id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isRepeat() {
        return repeat;
    }
}

