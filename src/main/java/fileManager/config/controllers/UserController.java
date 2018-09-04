package fileManager.config.controllers;

import fileManager.app.dto.UserDto;
import fileManager.app.models.User;
import fileManager.app.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public UserDto getUserInfo(HttpServletRequest request, HttpServletResponse response) {
       HttpSession session = request.getSession(false);
       UserDetails userDetails = (UserDetails) session.getAttribute("user");
       User user = userService.findUserByLogin(userDetails.getUsername());
       return user != null ? new UserDto(user.getLogin()) : null;
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@RequestBody UserDto user,  HttpServletRequest request, HttpServletResponse response){
        User userEntity = new User(user.getLogin(), user.getMail(), user.getPassword());
        userService.createUser(UUID.randomUUID().toString(), user.getLogin(), user.getMail(), user.getPassword());
        if (userService.isUserExist(userEntity)) {
            response.setStatus(HttpStatus.OK.value());
        } else response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}