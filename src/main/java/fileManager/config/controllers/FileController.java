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
        UserDetails user = (UserDetails) session.getAttribute("user");
        List<UploadFile> savedFiles = fileService.findFileForUser(user.getUsername());

        List<FileDto> listFile = new ArrayList<FileDto>();
        for (UploadFile file: savedFiles) {
            if (file.getReplace() == null)
                listFile.add(new FileDto(file.getId(), file.getNameFile(), file.getUrlFile(), file.getType(), file.getSize(), file.getStatus(), file.getInfo(), file.getCount()));
        }
        return new FilesDto(listFile);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void addToBasketFiles(@RequestBody List<Integer> deletedFileIds, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        UserDetails user = (UserDetails) session.getAttribute("user");
        fileService.addToBasketFilesById(user.getUsername(), deletedFileIds);
        response.setStatus(HttpStatus.OK.value());
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void returnFromBasketFiles(@RequestBody List<Integer> returnFilesIds,HttpServletRequest request, HttpServletResponse response ) {
        HttpSession session = request.getSession(false);
        UserDetails user = (UserDetails) session.getAttribute("user");
        fileService.returnFromBasketFilesById(user.getUsername(), returnFilesIds);
        response.setStatus(HttpStatus.OK.value());
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteFiles(@RequestBody List<Integer> deletedFileIds, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        UserDetails user = (UserDetails) session.getAttribute("user");
        fileService.deleteFilesById(user.getUsername(), deletedFileIds);
        response.setStatus(HttpStatus.OK.value());
    }



    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/replace", method = RequestMethod.POST)
    public void addToBasketFiles(@RequestBody String nameFile, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        UserDetails user = (UserDetails) session.getAttribute("user");
        UploadFile file = fileService.findFileByFileName(nameFile);
        if (file.getUser().getLogin().equals(user.getUsername())) {

        }
        else
            response.setStatus(HttpStatus.BAD_REQUEST.value());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
