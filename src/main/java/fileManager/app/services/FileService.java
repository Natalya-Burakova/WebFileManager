package fileManager.app.services;


import fileManager.app.dao.FileDaoImpl;

import fileManager.app.dao.UserDaoImpl;
import fileManager.app.models.UploadFile;
import fileManager.app.models.User;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileService {
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private FileDaoImpl fileDao = FileDaoImpl.getInstance();

    private static final FileService fileService = new FileService();
    private FileService(){}
    public static FileService getInstance(){ return fileService; }

    static {
        //monitorFile();
    }

    public List<UploadFile> findFileForUser(String login) {
        return fileDao.findFilesForUser(userDao.findUserByLogin(login));
    }


    public void saveFile(User user, MultipartFile file, String  urlFile) throws IOException {
        UploadFile uploadFile = new UploadFile(file.getOriginalFilename(), urlFile, user, file.getBytes(), file.getContentType(), false, file.getSize(), new Date(), "nothing", 0);
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

    public void returnFromBasketFilesById(String login, List<Integer> fileReturnToBasketIds) {
        for (Integer id: fileReturnToBasketIds) {
            UploadFile uploadFile = fileDao.getFileById(id);
            User user = UserService.getInstance().findUserByLogin(uploadFile.getUser().getLogin());
            if (login.equals(user.getLogin())) {
                uploadFile.setStatus(false);
                uploadFile.setData(new Date());
                fileDao.update(uploadFile);
            }
        }
    }

    public boolean isFileExist(UploadFile file) {
        return fileDao.isFileExist(file);
    }

    public UploadFile findFileByFileName(String fileName) {
        return fileDao.getFileByName(fileName);
    }

    public UploadFile findFileByUrlFile(String urlFile) {
        return fileDao.getFileByUrlFile(urlFile);
    }

    public void updateCount(UploadFile file) {
        fileDao.update(file);
    }

    private static  void monitorFile() {
        Thread thread = new Thread(() -> {
            while(true) {
                List<UploadFile> files = FileDaoImpl.getInstance().findAll();
                for (UploadFile file: files) {
                    long time = new Date().getTime()-file.getData().getTime();
                    int  days =  (int)(time/ (24 * 60 * 60 * 1000));
                    if (days>=4){
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(file.getId());
                        FileService.getInstance().deleteFilesById(file.getUser().getLogin(), list);
                    }
                }
            }
        });
        thread.run();

    }

}
