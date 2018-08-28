package fileManager.config.controllers;

import fileManager.app.dto.UserDto;
import fileManager.app.models.User;
import fileManager.app.services.UserService;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public UserDto getUserInfo(HttpServletRequest request, HttpServletResponse response) {
       HttpSession session = request.getSession(false);
       UserDetails userDetails = (UserDetails) session.getAttribute("user");
       User user = UserService.getInstance().findUserByLogin(userDetails.getUsername());
       return user != null ? new UserDto(user.getLogin()) : null;
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@RequestBody UserDto user,  HttpServletRequest request, HttpServletResponse response){
        User userEntity = new User(user.getLogin(), user.getMail(), user.getPassword());
        UserService.getInstance().createUser(user.getLogin(), user.getMail(), user.getPassword());
        if (UserService.getInstance().isUserExist(userEntity)) {
            response.setStatus(HttpStatus.OK.value());
        } else response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}