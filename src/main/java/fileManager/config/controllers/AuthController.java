package fileManager.config.controllers;


import fileManager.app.dto.UserDto;
import fileManager.app.models.User;
import fileManager.app.services.FileService;
import fileManager.app.services.UserDetailsService;
import fileManager.app.services.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;


@Controller
@RequestMapping("/authenticate")
public class AuthController {

    private UserDetailsService userDetailsService = UserDetailsService.getInstance();

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void authenticate(@RequestBody UserDto user, HttpServletRequest request, HttpServletResponse response){
        if (user.getLogin() == null || user.getPassword() == null)
            response.setStatus(HttpStatus.NOT_FOUND.value());
        else {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
            if (new BCryptPasswordEncoder().matches(user.getPassword(), userDetails.getPassword())) {
                HttpSession session = request.getSession(false);
                if (session != null) session.invalidate();
                session = request.getSession();
                session.setAttribute("user", userDetails);
                response.setStatus(HttpStatus.OK.value());
                FileService.monitorFile();
            }
            else
                response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
