package fileManager.config.root;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@ComponentScan({"fileManager"})
@Configuration
@ImportResource("classpath:/spring-bean-config.xml")
public class SpringRootConfig {

    @Autowired
    DataSource dataSource;

    @Value("${jdbc.url}")
    String url;

    @Value("${jdbc.user}")
    String user;

    @Value("${jdbc.password}")
    String password;


    @Bean
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @PostConstruct
    public void startDBManager() {
        DatabaseManagerSwing.main(new String[] { "--url", url, "--user", user, "--password", password });
    }
}
