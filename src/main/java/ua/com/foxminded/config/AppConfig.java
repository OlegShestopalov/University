//package ua.com.foxminded.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//
//@Configuration
//@ComponentScan("ua.com.foxminded")
//@PropertySource("classpath:application.properties")
//public class AppConfig {
//
////    private final Environment environment;
////
////    @Autowired
////    public AppConfig(Environment environment) {
////        this.environment = environment;
////    }
//
//    private final String URL = "url";
//    private final String USER = "dbuser";
//    private final String DRIVER = "driver";
//    private final String PASSWORD = "dbpassword";
//
//    @Bean
//    public DataSource dataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.url(URL);
//        dataSourceBuilder.username(USER);
//        dataSourceBuilder.password(PASSWORD);
//        dataSourceBuilder.driverClassName(DRIVER);
//        return dataSourceBuilder.build();
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }
//}
