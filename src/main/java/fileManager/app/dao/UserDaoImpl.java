package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;
import fileManager.config.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao{

    @Override
    public Optional<User> find(Integer id) {
        return Optional.of(HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id));
    }

    @Override
    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        if (find(id).isPresent())
            session.delete(find(id).get());
        tx1.commit();
        session.close();
    }

    @Override
    public UploadFile findFileById(Integer id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(UploadFile.class, id);
    }

    @Override
    public List<User> findAll() {
        List<User> users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }
}
