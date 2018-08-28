package fileManager.config;

import fileManager.config.db.DatabaseConfig;
import fileManager.config.root.SpringRootConfig;
import fileManager.config.servlets.ServletContextConfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{SpringRootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() { return new Class<?>[] {ServletContextConfig.class}; }

    @Override
    protected String[] getServletMappings() { return new String[]{"/"}; }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("spring.profiles.active", "hsql");
    }
}

