package fileManager.config.controllers;

import fileManager.app.dto.FileDto;
import fileManager.app.dto.FilesDto;
import fileManager.app.models.UploadFile;
import fileManager.app.models.User;
import fileManager.app.services.FileService;

import fileManager.app.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
        List<Integer> idList = new ArrayList<Integer>();
        for (UploadFile file: savedFiles) {
            if (!file.getStatus().equals("true") && !file.getStatus().equals("false")) {
                Integer id = Integer.parseInt(file.getStatus().substring(file.getStatus().lastIndexOf("/")+1));
                idList.add(id);
            }
        }
        for (UploadFile file: savedFiles) {
            String urlFile = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/")+1)+file.getNameFile();
            if (file.getStatus().equals("true") || file.getStatus().equals("false")) {
                if (file.getStatus().equals("false") && idList.contains(file.getId()))
                    listFile.add(new FileDto(file.getId(), file.getNameFile(), urlFile, file.getType(), file.getFile().length, Boolean.valueOf(file.getStatus()), file.getInfo(), file.getCount(), true, false));
                else if (file.getStatus().equals("false"))
                    listFile.add(new FileDto(file.getId(), file.getNameFile(), urlFile, file.getType(), file.getFile().length, Boolean.valueOf(file.getStatus()), file.getInfo(), file.getCount(), false, true));
                else
                    listFile.add(new FileDto(file.getId(), file.getNameFile(), urlFile, file.getType(), file.getFile().length, Boolean.valueOf(file.getStatus()), file.getInfo(), file.getCount(), true, true));
            }
        }
        return new FilesDto(listFile);
    }


    @ResponseBody
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public void uploadFile(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        UserDetails userDetails = (UserDetails) session.getAttribute("user");
        User user = UserService.getInstance().findUserByLogin(userDetails.getUsername());
        if (!FileService.getInstance().isFileExist(new UploadFile(file.getOriginalFilename()))) {
            FileService.getInstance().saveFile(user, file);
            response.setStatus(HttpStatus.OK.value());
        }
        else
            response.setStatus(HttpStatus.BAD_REQUEST.value());

    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteFiles(@RequestBody List<Integer> deletedFileIds, HttpServletRequest request, HttpServletResponse response) {
        fileService.deleteFilesById(deletedFileIds);
        response.setStatus(HttpStatus.OK.value());
    }



    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/addToBasket", method = RequestMethod.POST)
    public void addToBasketFiles(@RequestBody List<Integer> deletedFileIds, HttpServletRequest request, HttpServletResponse response) {
        fileService.addToBasketFilesById(deletedFileIds);
        response.setStatus(HttpStatus.OK.value());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFile(HttpServletRequest request, HttpServletResponse response) {
        String nameFile = request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1);
        if (fileService.isFileExist(new UploadFile(nameFile))) {
            UploadFile file = fileService.findFileByFileName(nameFile);
            file.setCount(file.getCount() + 1);
            fileService.updateFile(file);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData(nameFile, nameFile);
            try {
                headers.setContentType(MediaType.parseMediaType(file.getType()));
            } catch (NullPointerException e) { return ResponseEntity.ok(null); }
            return new ResponseEntity<byte[]>(file.getFile(), headers, HttpStatus.OK);
        }
        else return ResponseEntity.ok(null);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/returnFromBasket", method = RequestMethod.POST)
    public void returnFromBasketFiles(@RequestBody List<Integer> returnFilesIds,HttpServletRequest request, HttpServletResponse response ) {
        fileService.returnFromBasketFilesById(returnFilesIds);
        response.setStatus(HttpStatus.OK.value());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/rename/{urlFile}", method = RequestMethod.PUT)
    public void renameFile(@RequestBody String information, HttpServletRequest request, HttpServletResponse response) {
        UploadFile file = fileService.findFileByFileName(request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/")+1));
        file.setNameFile(information);
        fileService.updateFile(file);
        response.setStatus(HttpStatus.OK.value());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/replace/{fileName}", method = RequestMethod.POST)
    public void replaceFile(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        UserDetails userDetails = (UserDetails) session.getAttribute("user");
        User user = UserService.getInstance().findUserByLogin(userDetails.getUsername());

        String nameFile = request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1);
        UploadFile uploadFile =  FileService.getInstance().findFileByFileName(nameFile);

        if (uploadFile!=null && !fileService.isFileExist(new UploadFile(file.getOriginalFilename()))) {
            fileService.saveFile(user, file);
            if (fileService.isFileExist(new UploadFile(file.getOriginalFilename()))) {
                uploadFile.setStatus("replace/" + fileService.findFileByFileName(file.getOriginalFilename()).getId());
                uploadFile.setData(new Date());
                fileService.updateFile(uploadFile);
                response.setStatus(HttpStatus.OK.value());
            }
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/undoreplace/{fileName}", method = RequestMethod.GET)
    public void undoReplaceFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        UserDetails userDetails = (UserDetails) session.getAttribute("user");
        User user = UserService.getInstance().findUserByLogin(userDetails.getUsername());

        String nameFile = request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1);
        UploadFile uploadFile =  FileService.getInstance().findFileByFileName(nameFile);

        List<UploadFile> files = fileService.findFileForUser(user.getLogin());

        for (UploadFile file: files) {
            if (!file.getStatus().equals("true") && !file.getStatus().equals("false")) {
                if (uploadFile.getId() == Integer.parseInt(file.getStatus().substring(file.getStatus().lastIndexOf("/") + 1))) {
                    file.setStatus("false");
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(uploadFile.getId());
                    fileService.deleteFilesById(list);
                    fileService.updateFile(file);
                    response.setStatus(HttpStatus.OK.value());
                }
            }
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/info/{fileName}", method = RequestMethod.PUT)
    public void addFileInfo(@RequestBody String info, HttpServletResponse response, HttpServletRequest request){
        String fileName = request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/")+1);
        if (FileService.getInstance().isFileExist(new UploadFile(fileName))) {
            UploadFile file = fileService.findFileByFileName(fileName);
            file.setInfo(info);
            fileService.updateFile(file);
            response.setStatus(HttpStatus.OK.value());
        }
        else
            response.setStatus(HttpStatus.NOT_FOUND.value());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(10 * 1024 * 1024);
        return multipart;
    }

}
