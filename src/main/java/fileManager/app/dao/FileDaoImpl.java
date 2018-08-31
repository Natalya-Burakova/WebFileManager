package fileManager.app.dao;

import fileManager.app.models.UploadFile;
import fileManager.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FileDaoImpl implements FileDao{

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //language=SQL
    private final String SQL_SELECT_ALL = "select * from new_file";
    //language=SQL
    private final String INSERT_FILE = "insert into new_file(id, count_down, data, file,info, name_file, status, type, user_id) values (:id, :count_down, :data, :file, :info, :name_file, :status, :type, :user_id)";
    //language=SQL
    private final String DELETE_FILE = "delete from new_file WHERE name_file = :name_file";
    //language=SQL
    private final String FIND_FILES_FOR_USER = "SELECT * FROM new_file WHERE new_file.user_id = :user_id";
    //language=SQL
    private final String UPDATE_COUNT = "update new_file set count_down = :count_down where new_file.name_file = :name_file";
    //language=SQL
    private final String UPDATE_NAME = "update new_file set name_file = :new_name where new_file.name_file = :name_file";
    //language=SQL
    private final String UPDATE_STATUS_AND_DATA = "update new_file set status =:status, data = :data where new_file.name_file = :name_file";
    //language=SQL
    private final String UPDATE_STATUS = "update new_file set status =:status where new_file.name_file = :name_file";
    //language=SQL
    private final String UPDATE_INFO = "update new_file set info =:info where new_file.name_file = :name_file";


    private RowMapper<UploadFile> fileRowMapper = (resultSet, i) -> {
        UploadFile uploadFile =  new UploadFile(resultSet.getString("id"), resultSet.getString("name_file"), resultSet.getInt("user_id"), resultSet.getBytes("file"), resultSet.getString("type"), resultSet.getString("status"), resultSet.getDate("data"), resultSet.getString("info"), resultSet.getInt("count_down"));
        return uploadFile;
    };

    @Override
    public List<UploadFile> findFilesForUser(User user) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user_id", user.getId());
        return namedParameterJdbcTemplate.query(FIND_FILES_FOR_USER, paramMap, fileRowMapper);
    }

    @Override
    public UploadFile getFileById(String id) {
        List<UploadFile> listAllFiles = findAll();
        for (UploadFile file : listAllFiles){
            if (file.getId().equals(id))
                return new UploadFile(id, file.getNameFile(), file.getUser(), file.getFile(), file.getType(), file.getStatus(), file.getData(), file.getInfo(), file.getCount());
        }
        return null;
    }

    @Override
    public boolean isFileExist(UploadFile fileUpload) {
        List<UploadFile> listAllFiles = findAll();
        for (UploadFile file : listAllFiles){
            if (file.getNameFile().equals(fileUpload.getNameFile()))
                return true;
        }
        return false;
    }

    @Override
    public UploadFile getFileByName(String nameFile) {
        List<UploadFile> listAllFiles = findAll();
        for (UploadFile file : listAllFiles){
            if (file.getNameFile().equals(nameFile))
                return new UploadFile(file.getId(), nameFile, file.getUser(), file.getFile(), file.getType(), file.getStatus(), file.getData(), file.getInfo(), file.getCount());
        }
        return null;
    }


    @Override
    public List<UploadFile> findAll() {
        return namedParameterJdbcTemplate.query(SQL_SELECT_ALL, fileRowMapper);
    }

    @Override
    public int save(UploadFile model) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", model.getId());
        paramMap.put("count_down", model.getCount());
        paramMap.put("data", model.getData());
        paramMap.put("file", model.getFile());
        paramMap.put("info", model.getInfo());
        paramMap.put("name_file", model.getNameFile());
        paramMap.put("status", model.getStatus());
        paramMap.put("type", model.getType());
        paramMap.put("user_id", model.getUser());
        return namedParameterJdbcTemplate.update(INSERT_FILE, paramMap);
    }

    @Override
    public int update(UploadFile model) { return -1; }


    @Override
    public int updateCount(UploadFile model) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("count_down", model.getCount());
        paramMap.put("name_file", model.getNameFile());
        return namedParameterJdbcTemplate.update(UPDATE_COUNT, paramMap);
    }

    @Override
    public int updateNameFile(String oldName, UploadFile model) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("new_name", model.getNameFile());
        paramMap.put("name_file", oldName);
        return namedParameterJdbcTemplate.update(UPDATE_NAME, paramMap);
    }

    @Override
    public int updateStatusAndData(UploadFile model) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name_file", model.getNameFile());
        paramMap.put("status", model.getStatus());
        paramMap.put("data", model.getData());
        return namedParameterJdbcTemplate.update(UPDATE_STATUS_AND_DATA, paramMap);
    }

    @Override
    public int updateStatus(UploadFile model) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name_file", model.getNameFile());
        paramMap.put("status", model.getStatus());
        return namedParameterJdbcTemplate.update(UPDATE_STATUS, paramMap);
    }

    @Override
    public int updateInfo(UploadFile model) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name_file", model.getNameFile());
        paramMap.put("info", model.getInfo());
        return namedParameterJdbcTemplate.update(UPDATE_INFO, paramMap);
    }

    @Override
    public int delete(UploadFile model) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name_file", model.getNameFile());
        return namedParameterJdbcTemplate.update(DELETE_FILE, paramMap);
    }
}
