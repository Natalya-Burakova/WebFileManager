package fileManager.app.dao;

import fileManager.app.models.User;
import fileManager.app.models.UploadFile;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User> {
    Optional<User> findUserByLogin(String login);
    boolean isLoginAvailable(String login);
    List<UploadFile> findFilesById(Integer id);
}