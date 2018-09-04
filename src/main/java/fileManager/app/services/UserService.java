package fileManager.app.services;


import fileManager.app.dao.UserDaoImpl;

import fileManager.app.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

import static fileManager.app.services.Validation.assertMatches;
import static fileManager.app.services.Validation.assertMinimumLength;
import static fileManager.app.services.Validation.assertNotBlank;

@Service
public class UserService {

    private static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}");
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


    @Autowired
    private UserDaoImpl usersDao;

    public void createUser(String id, String login, String mail, String password) {
        assertNotBlank(login, "Login cannot be empty.");
        assertMinimumLength(login, 6, "Login must have at least 6 characters.");
        assertNotBlank(mail, "Email cannot be empty.");
        assertMatches(mail, EMAIL_REGEX, "Invalid email.");
        assertNotBlank(password, "Password cannot be empty.");
        assertMatches(password, PASSWORD_REGEX, "Password must have at least 6 characters, with 1 numeric and 1 uppercase character.");

        if (!usersDao.isLoginAvailable(login)) throw new IllegalArgumentException("The login is not available.");

        User user = new User(id, login, mail, new BCryptPasswordEncoder().encode(password));
        usersDao.save(user);
    }

    public User findUserByLogin(String login) { return usersDao.findUserByLogin(login);}

    public boolean isUserExist(User user) { return usersDao.isLoginAvailable(user.getLogin()); }



}