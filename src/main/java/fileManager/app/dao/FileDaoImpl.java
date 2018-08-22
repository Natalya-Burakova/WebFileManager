package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;
import fileManager.config.utils.HibernateSessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.postgresql.util.PSQLException;

import java.util.List;

public class FileDaoImpl extends CrudDaoAbstract  implements FileDao{

    private static final FileDaoImpl fileDao = new FileDaoImpl();
    private FileDaoImpl(){}
    public static FileDaoImpl getInstance(){ return fileDao; }


    public void save(UploadFile uploadFile) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.save(uploadFile);
            tx1.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }


    @Override
    public List<UploadFile> findFilesForUser(User user) {
        Integer id = user.getId();
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from UploadFile where user = :user").setParameter("user", user);
        List<UploadFile> list = (List<UploadFile>) query.list();
        return list;
    }

    @Override
    public byte[] getDocumentFile(Integer id) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from UploadFile where id = :id").setInteger("id", id);
        if (query.list().isEmpty()) return null;
        return (byte[]) query.list().get(0);
    }


}
