package fileManager.app.security;
import fileManager.app.dao.UserRepository;
import fileManager.app.models.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByLogin(login);

        if (!user.isPresent()) {
            String message = "Username not found" + login;
            LOGGER.info(message);
            throw new UsernameNotFoundException(message);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        LOGGER.info("Found user in database: " + user.get());

        return new org.springframework.security.core.userdetails.User(login, user.get().getPassword(), authorities);
    }
}