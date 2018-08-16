package fileManager.app.dao;

import fileManager.app.models.User;
import fileManager.app.models.UploadFile;

public interface UserDao extends CrudDao<User> {
    UploadFile findFileById(Integer id);
}