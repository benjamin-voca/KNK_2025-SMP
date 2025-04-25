package models.dto;

import java.util.Date;

public class CreateRequestDto {
    private int id;
    private int student_id;
    private Date request_time;
    private boolean accepted;
    private boolean repeat;

    public CreateRequestDto(int id, int student_id, Date request_time, boolean accepted, boolean repeat) {
        this.id = id;
        this.student_id = student_id;
        this.request_time = request_time;
        this.accepted = accepted;
        this.repeat = repeat;
    }

    public void setRequest_time(Date request_time) {
        this.request_time = request_time;
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

    public Date getRequest_time() {
        return request_time;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isRepeat() {
        return repeat;
    }
}

