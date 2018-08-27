package fileManager.app.dao;

import fileManager.config.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public abstract class CrudDaoAbstract implements CrudDao{
    @Override
    public void save(Object model) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(model);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Object model) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(model);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Object model) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(model);
        transaction.commit();
        session.close();
    }

    @Override
    public List findAll() {
       return null;
    }
}
