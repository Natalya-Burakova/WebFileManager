package fileManager.app.dao;

public interface CrudDao<T> {
    int save(T model);
    int update(T model);
    int delete(T model);
}
