package fileManager.config.launch;

import fileManager.config.root.HibernateSessionFactory;
import org.apache.catalina.LifecycleException;
import org.apache.log4j.BasicConfigurator;

import org.hibernate.Session;


import javax.servlet.ServletException;


public class Main {
    public static void main(String[] args) throws ServletException, LifecycleException {
        BasicConfigurator.configure();

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
       // session.beginTransaction();

        //User contactEntity = new User();

       // contactEntity.setLogin("rdjrtj");
       // contactEntity.setPassword("Nick");

      //  session.save(contactEntity);

       // session.getTransaction().commit();
        session.close();



        /*String webappDirLocation = "webapp";
        Tomcat tomcat = new Tomcat();


        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
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
        tomcat.getServer().await();*/
    }
}
