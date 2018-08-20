package fileManager.app.dao;

import fileManager.app.models.User;
import fileManager.app.models.UploadFile;

import java.util.List;


public interface UserDao extends CrudDao<User> {
    User findUserByLogin(String login);
    boolean isLoginAvailable(String login);
    List<UploadFile> findFilesById(Integer id);
    boolean isUserExist(User user);
    User findUserById(Integer id);
}