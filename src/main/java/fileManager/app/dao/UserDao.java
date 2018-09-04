package fileManager.app.dao;

import fileManager.app.models.User;



public interface UserDao extends CrudDao<User>{
    User findUserByLogin(String login);
    boolean isLoginAvailable(String login);
}