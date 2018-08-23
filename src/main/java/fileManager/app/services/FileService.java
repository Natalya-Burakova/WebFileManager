package fileManager.app.services;


import fileManager.app.dao.FileDaoImpl;

import fileManager.app.dao.UserDaoImpl;
import fileManager.app.models.UploadFile;
import fileManager.app.models.User;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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


    public void saveFile(User user, MultipartFile file, String  urlFile) throws IOException {
        UploadFile uploadFile = new UploadFile(file.getOriginalFilename(), urlFile, user, file.getBytes(), file.getContentType(), false, file.getSize(), new Date());
        fileDao.save(uploadFile);
        user.addFile(uploadFile);
    }

    public void deleteFilesById(String login, List<Integer> fileDeleteIds) {
        for (Integer id: fileDeleteIds) {
            UploadFile uploadFile = fileDao.getFileById(id);
            User user = UserService.getInstance().findUserByLogin(uploadFile.getUser().getLogin());
            if (login.equals(user.getLogin())) {
                fileDao.delete(uploadFile);
                user.removeFile(uploadFile);
            }
        }
    }


    public void addToBasketFilesById(String login, List<Integer> fileAddToBasketIds) {
        for (Integer id: fileAddToBasketIds) {
            UploadFile uploadFile = fileDao.getFileById(id);
            User user = UserService.getInstance().findUserByLogin(uploadFile.getUser().getLogin());
            if (login.equals(user.getLogin())) {
                uploadFile.setStatus(true);
                uploadFile.setData(new Date());
                fileDao.update(uploadFile);
            }
        }
    }

    public boolean isFileExist(UploadFile file) { return fileDao.isFileExist(file); }

    public UploadFile findFileByFileName(String fileName) { return  fileDao.getFileByName(fileName); }


}
