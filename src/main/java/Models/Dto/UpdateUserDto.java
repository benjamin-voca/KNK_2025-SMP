package Models.Dto;

public class UpdateUserDto {
    private int user_id;
    private String email;
    private String password_hash;

    public UpdateUserDto(int user_id, String email, String password_hash) {
        this.user_id = user_id;
        this.email = email;
        this.password_hash = password_hash;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
}
