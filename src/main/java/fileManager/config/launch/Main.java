package fileManager.config.launch;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import fileManager.app.dao.UserDao;
import fileManager.app.dao.UserDaoImpl;
import fileManager.app.models.User;
import fileManager.app.services.UserService;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.log4j.BasicConfigurator;


import javax.servlet.ServletException;
import java.io.File;


public class Main {
    public static void main(String[] args) throws ServletException, LifecycleException {
        BasicConfigurator.configure();
        String webappDirLocation = "webapp";
        Tomcat tomcat = new Tomcat();

        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        File additionWebInfClasses = new File("classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
