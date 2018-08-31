package fileManager.config;


import fileManager.config.root.SpringRootConfig;
import fileManager.config.servlets.ServletContextConfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;



public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{SpringRootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() { return new Class<?>[] {ServletContextConfig.class}; }

    @Override
    protected String[] getServletMappings() { return new String[]{"/"}; }

}

