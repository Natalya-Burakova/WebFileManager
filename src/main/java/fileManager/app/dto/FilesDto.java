package fileManager.app.dto;

import java.util.List;

public class FilesDto {

    List<FileDto> files;

    public FilesDto(List<FileDto> files) { this.files = files; }

    public void setFiles(List<FileDto> files) { this.files = files; }

    public List<FileDto> getFiles() { return files; }
}
