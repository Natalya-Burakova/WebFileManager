package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;
import fileManager.config.utils.HibernateSessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class FileDaoImpl extends CrudDaoAbstract  implements FileDao{

    private static final FileDaoImpl fileDao = new FileDaoImpl();
    private FileDaoImpl(){}
    public static FileDaoImpl getInstance(){ return fileDao; }


    @Override
    public List<UploadFile> findFilesForUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from UploadFile where user = :user").setParameter("user", user);
        List<UploadFile> list = (List<UploadFile>) query.list();
        user.setListAllUploadFile(list);
        session.close();
        return list;
    }

    @Override
    public UploadFile getFileById(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from UploadFile where id = :id").setInteger("id", id);
        if (query.list().isEmpty()) {
            session.close();
            return null;
        }
        UploadFile file =(UploadFile) query.list().get(0);
        session.close();
        return file;
    }

    @Override
    public boolean isFileExist(UploadFile file) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from UploadFile where nameFile = :nameFile").setString("nameFile", file.getNameFile());
        if (query.list().isEmpty()) {
            session.close();
            return false;
        }
        session.close();
        return true;
    }

    @Override
    public UploadFile getFileByName(String nameFile) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from UploadFile where nameFile = :nameFile").setString("nameFile", nameFile);
        if (query.list().isEmpty()) {
            session.close();
            return null;
        }
        UploadFile file = (UploadFile) query.list().get(0);
        session.close();
        return file;

    }


    @Override
    public List<UploadFile> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<UploadFile> files =  session.createQuery("From UploadFile").list();
        session.close();
        return files;
    }
}
