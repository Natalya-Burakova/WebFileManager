package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;

import java.util.List;

public interface FileDao extends CrudDao<UploadFile> {
    List<UploadFile> findFilesForUser(User user);

}
