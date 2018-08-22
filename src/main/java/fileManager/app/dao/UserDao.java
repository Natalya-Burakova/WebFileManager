package fileManager.app.dao;

import fileManager.app.models.User;
import fileManager.app.models.UploadFile;

import java.util.List;


public interface UserDao{
    User findUserByLogin(String login);
    boolean isLoginAvailable(String login);
    boolean isUserExist(User user);
}