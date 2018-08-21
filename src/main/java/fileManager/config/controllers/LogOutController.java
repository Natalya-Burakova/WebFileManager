package fileManager.config.controllers;



import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/logout")
public class LogOutController {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void logOut(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        session.invalidate();
        response.setStatus(HttpStatus.OK.value());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
