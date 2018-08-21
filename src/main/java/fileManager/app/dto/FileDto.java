package fileManager.app.dto;


import fileManager.app.models.UploadFile;

import java.util.List;
import java.util.stream.Collectors;

public class FileDto {

    private Integer id;
    private String urlFile;


    public FileDto(){}

    public FileDto(Integer id, String urlFile) {
        this.id = id;
        this.urlFile = urlFile;
    }

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return id; }

    public void setUrlFile(String urlFile) { this.urlFile = urlFile;}
    public String getUrlFile() { return urlFile; }

    public static FileDto mapFromFileEntity(UploadFile uploadFile) {
        return new FileDto(uploadFile.getId(), uploadFile.getUrlFile());
    }

    public static List<FileDto> mapFromFilesEntities(List<UploadFile> files) {
        return files.stream().map((file) -> mapFromFileEntity(file)).collect(Collectors.toList());
    }



}
