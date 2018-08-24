package fileManager.app.dao;

import fileManager.app.models.User;



public interface UserDao{
    User findUserByLogin(String login);
    boolean isLoginAvailable(String login);
    boolean isUserExist(User user);
}