package fileManager.app.services;

import fileManager.app.models.User;
import fileManager.app.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    private static final UserDetailsService userDetailsService= new UserDetailsService();

    private UserDetailsService(){}

    public static UserDetailsService getInstance(){ return userDetailsService; }

    private UserService userService = UserService.getInstance();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByLogin(username);
        if (user == null) {
            String message = "Username not found" + username;
            throw new UsernameNotFoundException(message);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);

    }
}
