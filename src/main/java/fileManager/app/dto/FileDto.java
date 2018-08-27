package fileManager.app.dto;


public class FileDto {

    private Integer id;
    private String nameFile;
    private String urlFile;
    private String type;
    private String info;
    private Integer size;
    private Integer count;
    private Boolean status;
    private Boolean newReplaceFile;
    private Boolean undoReplaceFile;

    public FileDto() {}

    public FileDto(Integer id, String nameFile, String urlFile, String type, Integer size, Boolean status, String info, Integer count, Boolean newReplaceFile, Boolean undoReplaceFile) {
        this.id = id;
        this.nameFile = nameFile;
        this.urlFile = urlFile;
        this.type = type;
        this.size = size;
        this.status = status;
        this.info = info;
        this.count = count;
        this.newReplaceFile = newReplaceFile;
        this.undoReplaceFile = undoReplaceFile;
    }

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return id; }

    public void setNameFile(String nameFile) { this.nameFile = nameFile;}
    public String getNameFile() { return nameFile; }

    public void setUrlFile(String urlFile) { this.urlFile = urlFile; }
    public String getUrlFile() { return urlFile; }

    public void setSize(Integer size) { this.size = size; }
    public Integer getSize() { return size; }

    public void setType(String type) { this.type = type; }
    public String getType() { return type; }

    public void setStatus(Boolean status) { this.status = status; }
    public Boolean getStatus() { return status; }

    public void setInfo(String info) { this.info = info; }
    public String getInfo() { return info; }

    public void setCount(Integer count) { this.count = count; }
    public Integer getCount() { return count; }


    public void setNewReplaceFile(Boolean newReplaceFile) { this.newReplaceFile = newReplaceFile; }
    public Boolean getNewReplaceFile() { return newReplaceFile; }

    public void setUndoReplaceFile(Boolean undoReplaceFile) { this.undoReplaceFile = undoReplaceFile; }
    public Boolean getUndoReplaceFile() { return undoReplaceFile; }
}
