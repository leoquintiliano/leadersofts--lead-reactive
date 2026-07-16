package br.com.apirest.leadersofts.leadcapture.infrastructure.config.hibernate;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

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

    public Uni<?> save(Object  entity, boolean save) {
        if(save)
            return this.getSessionFactory().withSession(session ->
                    session.persist(entity)
                            .chain(session::flush)
                            .replaceWith(entity)
            );
        else
            this.getSessionFactory().withSession(session -> session.merge(entity)).subscribe();
//            this.getSessionFactory().withSession(session -> session.merge(entity).onItem().call(session::flush)).subscribe();
        return Uni.createFrom().item(entity);
    }

    public void delete(Long id) {
        var result = this.getSessionFactory().withStatelessSession( session ->
                session.createNativeQuery("DELETE FROM Lead L WHERE L.id = :id")
                        .getResultList()
                        .invoke(leads -> leads.stream().forEach(System.out::println))
        ).await().indefinitely();
    }

}
