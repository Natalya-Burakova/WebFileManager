package fileManager.app.dto;

public class UserDto {

    private String login;
    private String mail;
    private String password;

    public UserDto(){}

    public UserDto(String login) { this.login = login; }

    public String getLogin() {
        return login;
    }
    public void setLogin(String username) {
        this.login = username;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) { this.password = password; }
}