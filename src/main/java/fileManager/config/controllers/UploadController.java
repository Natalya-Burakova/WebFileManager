package fileManager.config.controllers;


import fileManager.app.services.FileService;
import fileManager.app.services.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    FileService fileService = FileService.getInstance();

    @Bean(name = "filterMultipartResolver")
    public CommonsMultipartResolver multiPartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        return resolver;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void uploadFile(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletResponse response) throws IOException {
        fileService.save(UserService.getInstance().findUserByLogin("nataBur"),file);
        response.setStatus(HttpStatus.OK.value());
    }

    /*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity getDocument(@PathVariable Integer id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity(fileService.getDocumentFile(id), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List getDocument() {
        return  fileService.findFileForUser("nataBur");
    }*/

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(3 * 1024 * 1024);
        return multipart;
    }

}