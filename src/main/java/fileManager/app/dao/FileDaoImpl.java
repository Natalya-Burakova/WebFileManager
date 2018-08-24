package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;
import fileManager.config.utils.HibernateSessionFactoryUtil;
import org.hibernate.Query;

import java.util.List;

public class FileDaoImpl extends CrudDaoAbstract  implements FileDao{

    private static final FileDaoImpl fileDao = new FileDaoImpl();
    private FileDaoImpl(){}
    public static FileDaoImpl getInstance(){ return fileDao; }


    @Override
    public List<UploadFile> findFilesForUser(User user) {
        Integer id = user.getId();
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from UploadFile where user = :user").setParameter("user", user);
        List<UploadFile> list = (List<UploadFile>) query.list();
        user.setListAllUploadFile(list);
        return list;
    }

    @Override
    public UploadFile getFileById(Integer id) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from UploadFile where id = :id").setInteger("id", id);
        if (query.list().isEmpty()) return null;
        return (UploadFile) query.list().get(0);
    }



    @Override
    public boolean isFileExist(UploadFile file) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from UploadFile where nameFile = :nameFile").setString("nameFile", file.getNameFile());
        if (query.list().isEmpty()) return false;
        return true;
    }

    @Override
    public UploadFile getFileByName(String nameFile) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from UploadFile where nameFile = :nameFile").setString("nameFile", nameFile);
        if (query.list().isEmpty()) return null;
        return (UploadFile) query.list().get(0);
    }

    @Override
    public List<UploadFile> findAll() {
        List files =  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From UploadFile").list();
        return files;
    }
}
