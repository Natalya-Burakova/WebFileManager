package fileManager.app.dao;

import fileManager.app.models.User;



public interface UserDao extends CrudDao<User>{
    User findUserByLogin(String login);
    User findUserById(Integer id);
    boolean isLoginAvailable(String login);
}