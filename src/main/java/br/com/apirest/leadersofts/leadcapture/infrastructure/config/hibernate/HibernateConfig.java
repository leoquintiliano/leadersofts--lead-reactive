package br.com.apirest.leadersofts.leadcapture.infrastructure.config.hibernate;

//import org.hibernate.SessionFactory;
//import org.hibernate.reactive.stage.Stage;
//import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

//    @Value("${spring.r2dbc.username}")
//    private String user;
//
//    @Value("${spring.r2dbc.password}")
//    private String password;
//
//    @Bean
//    public Mutiny.SessionFactory sessionFactory() {
//
//        Properties props = new Properties();
//
////        String url = "jdbc:postgresql://localhost:5432/okta_lead_capture";
//        String jdbcUrl = System.getenv("DATABASE_URL");
//
//        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        props.setProperty("hibernate.show_sql", "true");
//        props.setProperty("hibernate.format_sql", "true");
//        props.setProperty("hibernate.hbm2ddl.auto", "create");
//        props.setProperty("hibernate.connection.url", jdbcUrl);
//        props.setProperty("hibernate.connection.username", user);
//        props.setProperty("hibernate.connection.password", password);
//
//        var hibernateConfiguration = new org.hibernate.cfg.Configuration();
//        hibernateConfiguration.setProperties(props);
//        hibernateConfiguration.addAnnotatedClass(Lead.class);
//
//        var serviceRegistry = new  ReactiveServiceRegistryBuilder().applySettings(hibernateConfiguration.getProperties()).build();
//        Mutiny.SessionFactory sessionFactory = hibernateConfiguration.buildSessionFactory(serviceRegistry).unwrap(Mutiny.SessionFactory.class);
//
//        return sessionFactory;
//    }

}
