package fileManager.app.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    T findUserById(Integer id);
    void save(T model);
    void update(T model);
    void delete(T model);
    List<T> findAll();

}
