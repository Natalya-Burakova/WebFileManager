package fileManager.config.controllers;

import fileManager.app.dto.FileDto;
import fileManager.app.dto.FilesDto;
import fileManager.app.models.UploadFile;
import fileManager.app.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService = FileService.getInstance();

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public FilesDto loadFiles(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        List<FileDto> listFile = new ArrayList<FileDto>();
        if (session!=null && session.getAttribute("user")!=null){
            UserDetails user = (UserDetails) session.getAttribute("user");
            List<UploadFile> savedFiles = fileService.findFileForUser(user.getUsername());
            for (UploadFile file: savedFiles)
                listFile.add(new FileDto(file.getId(), file.getNameFile(), file.getUrlFile(), file.getType(),file.getSize(), file.getStatus()));
        }
        return new FilesDto(listFile);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void addToBasketFiles(@RequestBody List<Integer> deletedFileIds, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session!=null && session.getAttribute("user")!=null) {
            UserDetails user = (UserDetails) session.getAttribute("user");
            fileService.addToBasketFilesById(user.getUsername(), deletedFileIds);
            response.setStatus(HttpStatus.OK.value());
        }
        else
            response.setStatus(HttpStatus.NOT_FOUND.value());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteFiles(@RequestBody List<Integer> deletedFileIds, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session!=null && session.getAttribute("user")!=null) {
            UserDetails user = (UserDetails) session.getAttribute("user");
            fileService.deleteFilesById(user.getUsername(), deletedFileIds);
            response.setStatus(HttpStatus.OK.value());
        }
        else
            response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
