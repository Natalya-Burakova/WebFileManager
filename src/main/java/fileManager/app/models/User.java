package fileManager.app.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "new_user")
@NamedQueries({
        @NamedQuery(
                name = User.FIND_USER_BY_ID,
                query = "select u from User u where id = :id"
        ),
        @NamedQuery(
                name = User.FIND_USER_BY_LOGIN,
                query = "select u from  User u where login = :login"
        )
})
public class User {

    public static final String FIND_USER_BY_ID = "user.findUserById";
    public static final String FIND_USER_BY_LOGIN = "user.findUserByLogin";

    private Integer id;
    private String login;
    private String mail;
    private String password;
    private List<UploadFile> listAllUploadFile;


    public User(){}

    public User(String login, String mail, String password) {
        this.login = login;
        this.mail = mail;
        this.password = password;
        listAllUploadFile = new ArrayList<UploadFile>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 50)
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "mail", nullable = false, length = 50)
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<UploadFile> getListAllUploadFile() { return listAllUploadFile; }
    public void setListAllUploadFile(List<UploadFile>listAllUploadFile) { this.listAllUploadFile = listAllUploadFile; }

    public void addFile(UploadFile file) { file.setUser(this);listAllUploadFile.add(file); }

    public void removeFile(UploadFile file) {
        listAllUploadFile.remove(file);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (id != that.id) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() { return "User{" + "login='" + login + '\'' + ", mail='" + mail + '\'' + '}'; }

}