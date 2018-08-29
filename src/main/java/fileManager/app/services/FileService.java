package fileManager.app.services;


import fileManager.app.dao.FileDaoImpl;

import fileManager.app.dao.UserDaoImpl;
import fileManager.app.models.UploadFile;
import fileManager.app.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private FileDaoImpl fileDao;

    public List<UploadFile> findFileForUser(String login) {
        return fileDao.findFilesForUser(userDao.findUserByLogin(login));
    }

    public void saveFile(User user, MultipartFile file) throws IOException {
        UploadFile uploadFile = new UploadFile(file.getOriginalFilename(), user.getId(), file.getBytes(), file.getContentType(), "false", new Date(), "nothing", 0);
        fileDao.save(uploadFile);
    }

    public void deleteFiles(List<String> fileDelete) {
        for (String name: fileDelete) {
            UploadFile uploadFile = fileDao.getFileByName(name);
            fileDao.delete(uploadFile);
        }
    }

    public void addToBasketFiles(List<String> fileAddToBasket) {
        for (String name: fileAddToBasket) {
            UploadFile uploadFile = fileDao.getFileByName(name);
            uploadFile.setStatus("true");
            uploadFile.setData(new Date());
            fileDao.updateStatusAndData(uploadFile);
        }
    }

    public void returnFromBasketFiles(List<String> fileReturnToBasket) {
        for (String name: fileReturnToBasket) {
            UploadFile uploadFile = fileDao.getFileByName(name);
            uploadFile.setStatus("false");
            uploadFile.setData(new Date());
            fileDao.updateStatusAndData(uploadFile);
        }
    }

    public boolean isFileExist(UploadFile file) {
        return fileDao.isFileExist(file);
    }

    public UploadFile findFileByFileName(String fileName) {
        return fileDao.getFileByName(fileName);
    }

    public UploadFile findFileById(Integer id) { return fileDao.getFileById(id); }

    public void updateCount(UploadFile file) {
        fileDao.updateCount(file);
    }

    public void updateNameFile(String oldName, UploadFile file) {
        fileDao.updateNameFile(oldName, file);
    }

    public void updateStatusAndData(UploadFile file) {
        fileDao.updateStatusAndData(file);
    }

    public void updateStatus(UploadFile file) {
        fileDao.updateStatus(file);
    }

    public void updateInfo(UploadFile file) {
        fileDao.updateInfo(file);
    }

    public void monitorFile() {
        List<UploadFile> files = fileDao.findAll();
        List<String> list = new ArrayList<String>();
        for (UploadFile file: files) {
            if (!file.getStatus().equals("false")) {
                long time = new Date().getTime() - file.getData().getTime();
                int days = (int) (time / (24 * 60 * 60 * 1000));
                if (days >= 4)
                    list.add(file.getNameFile());
            }
        }
        deleteFiles(list);
    }

}
