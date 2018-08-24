package fileManager.app.dto;


public class FileDto {

    private Integer id;
    private String nameFile;
    private String urlFile;
    private String type;
    private String info;
    private long size;
    private Boolean status;


    public FileDto(){}

    public FileDto(Integer id, String nameFile, String urlFile, String type, Long size, Boolean status, String info) {
        this.id = id;
        this.nameFile = nameFile;
        this.urlFile = urlFile;
        this.type = type;
        this.size = size;
        this.status = status;
        this.info = info;
    }

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return id; }

    public void setNameFile(String nameFile) { this.nameFile = nameFile;}
    public String getNameFile() { return nameFile; }

    public void setUrlFile(String urlFile) { this.urlFile = urlFile; }
    public String getUrlFile() { return urlFile; }

    public void setSize(Long size) { this.size = size; }
    public Long getSize() { return size; }

    public void setType(String type) { this.type = type; }
    public String getType() { return type; }

    public void setStatus(Boolean status) { this.status = status; }
    public Boolean getStatus() { return status; }

    public void setInfo(String info) { this.info = info; }
    public String getInfo() { return info; }

}
