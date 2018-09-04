package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;


import java.util.List;

public interface FileDao extends CrudDao<UploadFile>{
    List<UploadFile> findFilesForUser(User user);
    boolean isFileExist(UploadFile file);
    UploadFile getFileById(String id);
    UploadFile getFileByName(String fileName);
    List<UploadFile> findAll();
    int updateCount(UploadFile model);
    int updateNameFile(String oldName, UploadFile model);
    int updateStatusAndData(UploadFile model);
    int updateStatus(UploadFile model);
    int updateInfo(UploadFile model);
}
