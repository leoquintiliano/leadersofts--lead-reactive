package br.com.apirest.leadersofts.leadcapture.infrastructure.config.hibernate;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import io.vertx.core.spi.VerticleFactory;
import io.vertx.mutiny.core.Vertx;
import jakarta.persistence.*;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@EntityScan("br.com.apirest.leadersofts.leadcapture.infrastructure")
public class SessionConfig {

//    @Bean
//    public Mutiny.SessionFactory getPersistenceUnitsessionFactory() {
//            return Persistence.createEntityManagerFactory("leadPU")
//                    .unwrap(Mutiny.SessionFactory.class);
//    }

    @Bean
    public Vertx vertx(VerticleFactory verticleFactory) {
        Vertx vertx = Vertx.vertx();
        vertx.registerVerticleFactory(verticleFactory);
        return vertx;
    }

    @Value("${spring.r2dbc.username}")
    private String user;

    @Value("${spring.r2dbc.password}")
    private String password;

    @Value("${spring.r2dbc.url}")
    private String url;

    @PersistenceContext(name = "leadPU")
    private EntityManager entityManager;

    @Bean
    public Mutiny.SessionFactory sessionFactory() {

        Properties props = new Properties();

        String jdbcUrl = "jdbc:postgresql://localhost:5432/okta_lead_capture";
        String jdbcUrlEnv = System.getenv("DATABASE_URL");
//        props.setProperty("class","br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
//        props.setProperty("hibernate.hbm2ddl.auto", "create");
        props.setProperty("hibernate.connection.url", jdbcUrl);
        props.setProperty("hibernate.connection.username", user);
        props.setProperty("hibernate.connection.password", password);

        var hibernateConfiguration = new org.hibernate.cfg.Configuration();
        hibernateConfiguration.setProperties(props);
        hibernateConfiguration.addAnnotatedClass(Lead.class);

        var serviceRegistry = new ReactiveServiceRegistryBuilder().applySettings(hibernateConfiguration.getProperties()).build();
        Mutiny.SessionFactory sessionFactory = hibernateConfiguration.buildSessionFactory(serviceRegistry).unwrap(Mutiny.SessionFactory.class);

        var entities = getEntities();

        var provider = new HibernatePersistenceProvider();

//        provider.createContainerEntityManagerFactory( new jakarta.persistence.spi.PersistenceUnitInfo(null),new HashMap());

        return sessionFactory;
    }


//    @Bean
//    public void One() {
//        new HibernatePersistenceProvider()
//                .createContainerEntityManagerFactory(
//                        new jakarta.persistence.spi.PersistenceUnitInfo(persistenceUnitName),
//                        ImmutableMap.<String, Object>builder()
//                                .put(AvailableSettings.LOADED_CLASSES, getEntities())
//                                .build());
//        )
//    }

    private List<Class<?>> getEntities() {
        Set<Class<?>> entitiesInPackage = new Reflections("br.com.apirest.leadersofts.leadcapture.infrastructure.domain")
                .getTypesAnnotatedWith(Entity.class); // Annotated @Entity

        List entities = new ArrayList<>(entitiesInPackage);

        return entities;
    }

//    @Bean
////    @PersistenceContext(name = "leadPU")
//    public EntityManagerFactory entityManager() {
//
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("leadPU");
//        entityManagerFactory.createEntityManager();
//
//        return entityManager();
//    }


}
