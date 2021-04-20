package ua.com.foxminded.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

//    @Override
//    public void onStartup(ServletContext servletContext)
//            throws ServletException {
//        super.onStartup(servletContext);
//        ServletRegistration.Dynamic servlet = servletContext.addServlet(
//                "h2-console", new WebServlet());
//        servlet.setLoadOnStartup(2);
//        servlet.addMapping("/console/*");
//    }
}
