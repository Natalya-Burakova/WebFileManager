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


    public void saveFile(User user, MultipartFile file) throws IOException {
        UploadFile uploadFile = new UploadFile(file.getOriginalFilename(), user, file.getBytes(), file.getContentType(), "false", new Date(), "nothing", 0);
        fileDao.save(uploadFile);
        user.addFile(uploadFile);
    }

    public void deleteFilesById(List<Integer> fileDeleteIds) {
        for (Integer id: fileDeleteIds) {
            UploadFile uploadFile = fileDao.getFileById(id);
            fileDao.delete(uploadFile);
        }
    }

    public void addToBasketFilesById(List<Integer> fileAddToBasketIds) {
        for (Integer id: fileAddToBasketIds) {
            UploadFile uploadFile = fileDao.getFileById(id);
            uploadFile.setStatus("true");
            uploadFile.setData(new Date());
            fileDao.update(uploadFile);
        }
    }

    public void returnFromBasketFilesById(List<Integer> fileReturnToBasketIds) {
        for (Integer id: fileReturnToBasketIds) {
            UploadFile uploadFile = fileDao.getFileById(id);
            uploadFile.setStatus("false");
            uploadFile.setData(new Date());
            fileDao.update(uploadFile);
        }
    }

    public boolean isFileExist(UploadFile file) {
        return fileDao.isFileExist(file);
    }

    public UploadFile findFileByFileName(String fileName) {
        return fileDao.getFileByName(fileName);
    }

    public UploadFile findFileById(Integer id) {
        return fileDao.getFileById(id);
    }

    public void updateFile(UploadFile file) {
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
                        FileService.getInstance().deleteFilesById(list);
                    }
                }
            }
        });
        thread.run();

    }

}
