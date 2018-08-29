package fileManager.app.dao;

import fileManager.app.models.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao{

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //language=SQL
    private final String SQL_SELECT_ALL = "select * from new_user";
    //language=SQL
    private final String INSERT_USER = "insert into new_user(login, mail, password) values (:login, :mail, :password)";
    //language=SQL
    private final String DELETE_USER = "delete from new_user WHERE login = :login";


    private RowMapper<User> userRowMapper = (resultSet, i) -> {
        User user =  new User(Integer.parseInt(resultSet.getString("id")), resultSet.getString("login"), resultSet.getString("mail"), resultSet.getString("password"));
        return user;
    };


    @Override
    public int save(User user) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("login", user.getLogin());
        paramMap.put("mail", user.getMail());
        paramMap.put("password", user.getPassword());
        return namedParameterJdbcTemplate.update(INSERT_USER, paramMap);
    }

    @Override
    public int update(User model) { return -1; }

    @Override
    public int delete(User user) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("login", user.getLogin());
        return namedParameterJdbcTemplate.update(DELETE_USER, paramMap);
    }

    @Override
    public User findUserByLogin(String login) {
        List<User> listAllUsers = findAll();
        for (User user : listAllUsers){
            if (user.getLogin().equals(login))
                return new User(user.getId(), login, user.getMail(), user.getPassword());
        }
        return null;
    }

    @Override
    public User findUserById(Integer id) {
        System.out.println("find user");
        List<User> listAllUsers = findAll();
        for (User user : listAllUsers){
            if (user.getId().equals(id)) {
                System.out.println("nashel");
                return new User(id, user.getLogin(), user.getMail(), user.getPassword());
            }
        }
        return null;
    }

    @Override
    public boolean isLoginAvailable(String login) {
        List<User> listAllUsers = findAll();
        for (User user : listAllUsers){
            if (user.getLogin().equals(login))
                return false;
        }
        return true;
    }


    public List<User> findAll() { return namedParameterJdbcTemplate.query(SQL_SELECT_ALL, userRowMapper); }


}
