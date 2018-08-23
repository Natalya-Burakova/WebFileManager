package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;


import java.util.List;

public interface FileDao {
    List<UploadFile> findFilesForUser(User user);
    UploadFile getFileById(Integer id);
    boolean isFileExist(UploadFile file);
    UploadFile getFileByName(String fileName);
}
