package app.filemanager.tutorial;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * Конфигурация главной точки входа веб-приложения на Spring - сервлета DispatcherServlet
 * WebInitializer через цепочку наследования имплементирует WebApplicationInitializer
 * A за этим интерфейсом сдедит внутренний спринговый класс SpringServletContainerInitializer
 * Который запустится при старте контейнера сервлетов
 */


public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
