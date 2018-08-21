package fileManager.app.services;

import fileManager.app.dao.FileDao;
import fileManager.app.dao.FileDaoImpl;
import fileManager.app.dao.UserDao;
import fileManager.app.dao.UserDaoImpl;
import fileManager.app.models.UploadFile;
import fileManager.app.models.User;

import java.util.List;

public class FileService {
    private UserDao userDao = UserDaoImpl.getInstance();
    private FileDao fileDao = FileDaoImpl.getInstance();

    private static final FileService fileService = new FileService();
    private FileService(){}
    public static FileService getInstance(){ return fileService; }

    public List<UploadFile> findFileForUser(String login) {
        return fileDao.findFilesForUser(userDao.findUserByLogin(login));
    }
}
