package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;
import fileManager.config.utils.HibernateSessionFactoryUtil;
import org.hibernate.Query;

import java.util.List;

public class FileDaoImpl  implements FileDao{

    private static final FileDaoImpl fileDao = new FileDaoImpl();
    private FileDaoImpl(){}
    public static FileDaoImpl getInstance(){ return fileDao; }

    @Override
    public List<UploadFile> findFilesForUser(User user) {
        Integer id = user.getId();
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from UploadFile where user = :user").setParameter("user", user);
        List<UploadFile> list = (List<UploadFile>) query.list();
        return list;
    }


    @Override
    public void save(UploadFile model) {

    }

    @Override
    public void update(UploadFile model) {

    }

    @Override
    public void delete(UploadFile model) {

    }

    @Override
    public List<UploadFile> findAll() {
        return null;
    }
}
