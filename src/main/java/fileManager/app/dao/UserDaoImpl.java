package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;

import fileManager.config.utils.HibernateSessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoImpl implements UserDao{

    private static final UserDaoImpl userDao = new UserDaoImpl();
    private UserDaoImpl(){}
    public static UserDaoImpl getInstance(){ return userDao; }

    @Override
    public User findUserById(Integer id) { return (User) HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id); }

    @Override
    public User findUserByLogin(String login) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from User where login = :login").setString("login", login);
        if (query.list().isEmpty()) return null;
        return (User) query.list().get(0);
    }

    @Override
    public boolean isLoginAvailable(String login) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from User where login = :login").setString("login", login);
        if (query.list().isEmpty()) return true;
        return false;
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
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<UploadFile> findFilesById(Integer id) { return null; }

    @Override
    public boolean isUserExist(User user) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from User where login = :login").setString("login", user.getLogin());
        if (query.list().isEmpty()) return false;
        return true;
    }

    @Override
    public List<User> findAll() {
        List<User> users = (List<User>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }
}
