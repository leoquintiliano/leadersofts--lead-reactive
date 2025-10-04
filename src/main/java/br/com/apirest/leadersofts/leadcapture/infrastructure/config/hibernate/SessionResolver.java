package br.com.apirest.leadersofts.leadcapture.infrastructure.config.hibernate;

import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

@Component
public class SessionResolver {

    SessionFactory sessionFactory;

    @Value("${spring.r2dbc.username}")
    private String user;

    @Value("${spring.r2dbc.password}")
    private String password;

    private String urlJdbc = "jdbc:postgresql://localhost:5432/okta_lead_capture";

    private String jakartaUser = "jakarta.persistence.jdbc.user";

    private String jakartaPassword = "jakarta.persistence.jdbc.password";

    private String jakartaConnectionUrl = "jakarta.persistence.jdbc.url";

    public SessionFactory getSessionFactory() {
        var props = Map.of(this.jakartaConnectionUrl, this.urlJdbc,this.jakartaUser,this.user,this.jakartaPassword,this.password);
        this.sessionFactory = createEntityManagerFactory("leadPU",props)
                .unwrap(SessionFactory.class);
        return sessionFactory;
    }

}
