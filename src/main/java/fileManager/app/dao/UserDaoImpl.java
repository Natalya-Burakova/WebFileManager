package fileManager.app.dao;

import fileManager.app.models.User;

import fileManager.config.utils.HibernateSessionFactoryUtil;
import org.hibernate.Query;


public class UserDaoImpl extends CrudDaoAbstract implements UserDao{

    private static final UserDaoImpl userDao = new UserDaoImpl();
    private UserDaoImpl(){}
    public static UserDaoImpl getInstance(){ return userDao; }


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


}
