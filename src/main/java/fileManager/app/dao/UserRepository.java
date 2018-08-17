package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements UserDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<User> findUserById(Integer id) {
        List<User> users = em.createNamedQuery(User.FIND_USER_BY_ID, User.class).setParameter("id", id).getResultList();
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        List<User> users = em.createNamedQuery(User.FIND_USER_BY_LOGIN, User.class).setParameter("login", login).getResultList();
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }

    @Override
    public boolean isLoginAvailable(String login) {
        List<User> users = em.createNamedQuery(User.FIND_USER_BY_LOGIN, User.class).setParameter("login", login).getResultList();
        return users.isEmpty();
    }

    @Override
    public void save(User user) { em.merge(user);}

    @Override
    public void update(User user) { }

    @Override
    public void delete(Integer id) { }

    @Override
    public List<UploadFile> findFilesById(Integer id) { return null; }

    @Override
    public List<User> findAll() { return null; }
}
