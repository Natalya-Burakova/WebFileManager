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
            fileDao.updateStatusAndData(uploadFile);
        }
    }

    public void returnFromBasketFilesById(List<Integer> fileReturnToBasketIds) {
        for (Integer id: fileReturnToBasketIds) {
            UploadFile uploadFile = fileDao.getFileById(id);
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
        List<Integer> list = new ArrayList<Integer>();
        for (UploadFile file: files) {
            if (!file.getStatus().equals("false")) {
                long time = new Date().getTime() - file.getData().getTime();
                int days = (int) (time / (24 * 60 * 60 * 1000));
                if (days >= 4)
                    list.add(file.getId());
            }
        }
        deleteFilesById(list);
    }

}
