package fileManager.config.controllers;

import fileManager.app.dao.FileDaoImpl;
import fileManager.app.models.UploadFile;
import fileManager.app.models.User;
import fileManager.app.services.FileService;
import fileManager.app.services.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public void uploadFile(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        UserDetails userDetails = (UserDetails) session.getAttribute("user");
        User user = UserService.getInstance().findUserByLogin(userDetails.getUsername());
        if (!FileService.getInstance().isFileExist(new UploadFile(file.getOriginalFilename()))) {
            FileService.getInstance().saveFile(user, file, request.getRequestURL() + "/" + file.getOriginalFilename());
            response.setStatus(HttpStatus.OK.value());
        }
        else
            response.setStatus(HttpStatus.BAD_REQUEST.value());

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFile(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        String nameFile = request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1);

        UserDetails userDetails = (UserDetails) session.getAttribute("user");
        UploadFile file = FileService.getInstance().findFileByFileName(nameFile);

        if (userDetails.getUsername().equals(file.getUser().getLogin())) {
            file.setCount(file.getCount() + 1);
            FileService.getInstance().updateCount(file);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData(nameFile, nameFile);

            try {
                headers.setContentType(MediaType.parseMediaType(file.getType()));
            } catch (NullPointerException e) {
                return ResponseEntity.ok(null);
            }

            return new ResponseEntity<byte[]>(file.getFile(), headers, HttpStatus.OK);
        }
        else return ResponseEntity.ok(null);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void addFileInfo(@RequestBody String info, HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        UserDetails userDetails = (UserDetails) session.getAttribute("user");
        User user = UserService.getInstance().findUserByLogin(userDetails.getUsername());

        String[] mas = info.split("&");

        if (FileService.getInstance().isFileExist(new UploadFile(mas[0]))) {
            UploadFile file = FileService.getInstance().findFileByFileName(mas[0]);
            if (file.getUser().getLogin().equals(user.getLogin())) {
                file.setInfo(mas[1]);
                FileDaoImpl.getInstance().update(file);
                response.setStatus(HttpStatus.OK.value());
            }
            else
                response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        else
                response.setStatus(HttpStatus.NOT_FOUND.value());

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/rename", method = RequestMethod.POST)
    public void renameFile(@RequestBody String information, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        UserDetails user = (UserDetails) session.getAttribute("user");

        String[] mas = information.split("&");
        UploadFile file = FileService.getInstance().findFileByUrlFile(mas[1]);

        if (file.getUser().getLogin().equals(user.getUsername())) {
            file.setNameFile(mas[0]);
            file.setUrlFile(request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/")+1) + mas[0]);
            FileDaoImpl.getInstance().update(file);
            response.setStatus(HttpStatus.OK.value());
        }
        else
            response.setStatus(HttpStatus.NOT_FOUND.value());

    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/replace/{fileName}", method = RequestMethod.POST)
    public void replaceFile(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String nameFile = request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1);
        System.out.println(nameFile);
        response.setStatus(HttpStatus.OK.value());
    }


    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(10 * 1024 * 1024);
        return multipart;
    }


}