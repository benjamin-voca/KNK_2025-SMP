package models.dto;

public class CreateUserDto {
    private String first_name;
    private String last_name;
    private String email;
    private String password_hash;
    private String profile_picture_path;

    public CreateUserDto(String first_name, String last_name, String email, String password_hash, String profile_picture_path) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password_hash = password_hash;
        this.profile_picture_path = profile_picture_path;
    }

    public CreateUserDto(String first_name, String last_name, String email, String password_hash) {
        this(first_name, last_name, email, password_hash, null);
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public String getProfile_picture_path() {
        return profile_picture_path;
    }

    public void setProfile_picture_path(String profile_picture_path) {
        this.profile_picture_path = profile_picture_path;
    }
}