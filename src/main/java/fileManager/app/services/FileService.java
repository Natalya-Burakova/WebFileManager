package fileManager.app.services;


import fileManager.app.dao.FileDaoImpl;

import fileManager.app.dao.UserDaoImpl;
import fileManager.app.models.UploadFile;
import fileManager.app.models.User;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class FileService {
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private FileDaoImpl fileDao = FileDaoImpl.getInstance();

    private static final FileService fileService = new FileService();
    private FileService(){}
    public static FileService getInstance(){ return fileService; }

    public List<UploadFile> findFileForUser(String login) {
        return fileDao.findFilesForUser(userDao.findUserByLogin(login));
    }

    public byte[] getDocumentFile(Integer id) {
        return fileDao.getDocumentFile(id);
    }

    public void save(User user, MultipartFile file) throws IOException {
        UploadFile uploadFile = new UploadFile(file.getOriginalFilename(), user, file.getBytes());
        fileDao.save(uploadFile);
    }


}
