package fileManager.app.services;

import fileManager.app.dao.UserDaoImpl;
import fileManager.app.models.UploadFile;
import fileManager.app.models.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserDaoImpl usersDaoImpl = new UserDaoImpl();

    public UserService() { }

    public Optional<User> findUser(Integer id) { return usersDaoImpl.find(id); }

    public void saveUser(User user) { usersDaoImpl.save(user); }

    public void deleteUser(User user) { usersDaoImpl.delete(user.getId()); }

    public void updateUser(User user) { usersDaoImpl.update(user); }

    public List<User> findAllUsers() { return usersDaoImpl.findAll(); }

    public UploadFile findAutoById(Integer id) { return usersDaoImpl.findFileById(id); }

}
