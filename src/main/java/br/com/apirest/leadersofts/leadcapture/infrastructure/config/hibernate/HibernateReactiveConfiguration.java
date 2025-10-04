//package br.com.apirest.leadersofts.leadcapture.infrastructure.config;
//
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import jakarta.persistence.spi.PersistenceProvider;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class HibernateReactiveConfiguration {
//
////    private static final String r2dbcUrl = "r2dbc:postgresql://localhost:5432/";
//
//    private static final String jdbcUrl = "jdbc:postgresql://localhost:5432/";
//
//    @Value("${database_name}")
//    private String r2dbcDatabaseName;
//
//    @Value("${okta_user_name}")
//    private String r2dbcUser;
//
//    @Value("${okta_user_password}")
//    private String r2dbcPass;
//
//    @Bean
//    EntityManagerFactory entityManagerFactory(PersistenceProvider persistenceProvider)  {
//        return Persistence.createEntityManagerFactory("default", hibernateJpaProperties());
//    }
//
//    private Map<String, Object> hibernateJpaProperties() {
//
//        var props = new HashMap<String,Object>();
//
//        props.put("javax.persistence.jdbc.url", jdbcUrl.concat(r2dbcDatabaseName));
//        props.put("javax.persistence.jdbc.user", r2dbcUser);
//        props.put("javax.persistence.jdbc.password", r2dbcPass);
//        props.put("javax.persistence.schema-generation.database.action", "none"); // Perigoso - Danger
//        props.put("hibernate.highlight_sql",true);
//
////        props["javax.persistence.jdbc.url"] = mysql.jdbcUrl
////        props["javax.persistence.jdbc.user"] = mysql.username
////        props["javax.persistence.jdbc.password"] = mysql.password
////        props["javax.persistence.schema-generation.database.action"] = "drop-and-create"
////        props["hibernate.highlight_sql"] = true
//
//        return props;
//    }
//
//    @Bean
//    public PersistenceProvider getPersistenceprovider() {
//        return Provider;
//    }
//
//}
