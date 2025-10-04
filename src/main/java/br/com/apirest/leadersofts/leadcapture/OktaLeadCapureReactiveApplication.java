package br.com.apirest.leadersofts.leadcapture;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import io.r2dbc.spi.ConnectionFactory;

import io.smallrye.mutiny.Uni;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.List;
import java.util.Map;

@EnableWebFlux
@EnableR2dbcRepositories
@SpringBootApplication(scanBasePackages = "br.com.apirest.leadersofts.leadcapture")
@EntityScan({"br.com.apirest.leadersofts.leadcapture.infrastructure.domain", "org.hibernate.reactive.example"})
//@EnableScheduling
public class OktaLeadCapureReactiveApplication {

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
//		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
		return initializer;
	}

//	@PostConstruct
//	private void init() {
//		try{
//			this.sessionFactory = createEntityManagerFactory("leadPU").unwrap(Mutiny.SessionFactory.class);
//		} catch (Exception exception) {
//			throw new RuntimeException( "Error while trying to instantiate Session Factory to manage all the connections " + exception.getCause());
//		}
//	}

	public static void main(String[] args) {
		SpringApplication.run(OktaLeadCapureReactiveApplication.class, args);
	}

	private static Uni<List<Lead>> init() {

		String jakartaConnectionUrl = "jakarta.persistence.jdbc.url";
		String jakartaUser = "jakarta.persistence.jdbc.user";
		String jakartaPassword = "jakarta.persistence.jdbc.password";

		String url = "jdbc:postgresql://localhost:5432/okta_lead_capture";
		String user = "postgres";
		String pass = "postgres";

		var offset = 1;
		var limit = 10;

		var props = Map.of(jakartaConnectionUrl, url,jakartaUser,user,jakartaPassword,pass);

		Mutiny.SessionFactory sessionFactory = Persistence.createEntityManagerFactory("leadPU", props)
				.unwrap(Mutiny.SessionFactory.class);

		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Lead> query = cb.createQuery(Lead.class);

		return sessionFactory.withSession(session -> session.createQuery(query)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList());

	}

}
