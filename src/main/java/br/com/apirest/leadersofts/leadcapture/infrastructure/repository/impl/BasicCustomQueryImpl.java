package br.com.apirest.leadersofts.leadcapture.infrastructure.repository.impl;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead_;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.LeadRepositoryCustomQuery;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Entity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.reactive.mutiny.Mutiny;
import org.reflections.Reflections;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.util.Objects.nonNull;

//@RequiredArgsConstructor
public class BasicCustomQueryImpl implements LeadRepositoryCustomQuery {

    private List<Class<?>> getEntities() {

        Set<Class<?>> entitiesInPackage = new Reflections("br.com.apirest.leadersofts.leadcapture.infrastructure.domain")
                .getTypesAnnotatedWith(Entity.class); // Annotated @Entity

        Class<Lead> clazz = (Class<Lead>) new ArrayList<>(entitiesInPackage).get(0);

        return new ArrayList<>(entitiesInPackage);
    }

//    public Uni<List<Lead>> findByKeyword(int offset, int limit, String... params) {
//        CriteriaBuilder builder = this.sessionFactory.getCriteriaBuilder();
//        CriteriaQuery<Lead> criteria = builder.createQuery(Lead.class);
//        Root<Lead> leadRoot = criteria.from(Lead.class);
//
//        Arrays.stream(params).map( param -> {
//            if(param.equals("nome")) {
//                criteria.where(
//                        builder.or(
//                                builder.like(builder.upper(leadRoot.get(Lead_.nome)), "%" + param.toUpperCase() + "%" ),
//                                builder.like(builder.upper(leadRoot.get(Lead_.primeiroContato)), "%" + param.toUpperCase() + "%" )
//                        )
//                )
//                ;
//            }
//            return Strings.EMPTY;
//        }).collect(Collectors.toSet());
//
//        return this.sessionFactory.withSession(session -> session.createQuery(criteria)
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList());
//    }

//    private Specification<Lead> buildFilter() {
//        Specification<Lead> filterReturn;
//
//        filterReturn = Specification.where( this.enableFetchLeadsByName() )
//                .and( matchName())
//                .and( this.dateFilter.matchPrimeiroContato(filter))
//                .and( this.dateFilter.matchUltimoContato(filter))
//                .and( this.dateFilter.matchDataNascimento(filter))
//                .and(this.contatoFilter.matchCelular(filter))
//                .and(this.contatoFilter.matchCelular2(filter))
//                .and(this.contatoFilter.matchEmail(filter))
//                .and(this.contatoFilter.matchEndereco(filter))
//                .and(this.contatoFilter.matchUnidadeFederativa(filter))
//                .and(this.contatoFilter.matchCidade(filter))
//                .and(this.vendaFilter.matchCarroAtual1(filter))
//                .and(this.vendaFilter.matchCarroAtual2(filter))
//                .and(this.vendaFilter.matchCarroAtual3(filter))
//                .and(this.vendaFilter.matchCarroInteresse1(filter))
//                .and(this.vendaFilter.matchCarroInteresse2(filter))
//                .and(this.vendaFilter.matchCarroInteresse3(filter))
//                .and(this.vendaFilter.matchVendedor(filter))
//                .and(this.vendaFilter.matchObservacoes(filter))
//
//                .and(dateFilter.matchDataNascimento(filter))
//        ;
//
//        return filterReturn;
//
//    }

//    private Specification<Lead> enableFetchLeadsByName() {
//        return (root, query, cb) -> {
//            query.orderBy( cb.desc(root.get(Lead_.nome)) );
//            return null;
//        };
//    }

//    public Uni<List<Lead>> findWithFilters(HashMap<String, String> parameters, int offset, int limit) {
//        CriteriaBuilder cb = this.sessionFactory.getCriteriaBuilder();
//        CriteriaQuery<Lead> query = cb.createQuery(Lead.class);
//        Root<Lead> root = query.from(Lead.class);
//        TypedQuery k;
//
//        if(parameters.containsKey("nome")) {
//            query.where(
//                    cb.and(
//                        cb.or(cb.like(root.get(Lead.class.getName()), "%" + parameters.get("nome") + "%")),
//                        cb.or(cb.like(root.get(Lead.class.getName()), "%" + parameters.get("nome").toUpperCase() + "%"))
//                    )
//            );
//        }
//
//        if(parameters.containsKey("nome")) {
//
//        }
//
//        return this.sessionFactory.withSession(session -> session.createQuery(query)
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList());
//    }

//    public Specification<Lead> matchName() {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.nome())) {
//                query.distinct(true);
//                String termFilter = '%' + filter.nome().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.nome)), termFilter);
//            }
//            return null;
//        };
//    }

    private void prepareCriteriaQueryPredicates(List<Predicate> predicates, CriteriaBuilder cb, Root<Lead> root, LeadFilter filter) {
        if(nonNull(filter) && nonNull(filter.nome())) {
//            var nomeParam = nonNull(Lead_.nome) ? Lead_.nome : root.get("nome");
            if(nonNull(Lead_.nome)) {
                predicates.add(cb.like(cb.upper(root.get(Lead_.nome)), "%" + filter.nome().toUpperCase() + "%"));
            } else {
//                predicates.add(cb.like(cb.upper(root.get("nome")), "%" + filter.nome().toUpperCase() + "%"));
                predicates.add(cb.like(cb.upper(root.get(Lead_.NOME)), "%" + filter.nome().toUpperCase() + "%"));
            }
//            predicates.add(cb.like(cb.upper(root.get((String) nomeParam)), "%" + filter.nome().toUpperCase() + "%"));
        }

//        dateFilter.setDatePredicates(predicates, cb, root,filter);
//        contatoFilter.setContactPredicates(predicates, cb, (Root<Lead>) root,filter);
//        vendaFilter.setSalePredicates(predicates,cb, (Root<Lead>) root,filter);
    }

    public Uni<List<Lead>> findByKeywordFilter(LeadFilter filter, int offset, int limit) {

        String jakartaConnectionUrl = "jakarta.persistence.jdbc.url";
        String jakartaUser = "jakarta.persistence.jdbc.user";
        String jakartaPassword = "jakarta.persistence.jdbc.password";

        String url = "jdbc:postgresql://localhost:5432/okta_lead_capture";
        String user = "";
        String pass = "";

        var props = Map.of(jakartaConnectionUrl, "",jakartaUser,user,jakartaPassword,pass);

//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("leadPU");
        Mutiny.SessionFactory sessionFactory = createEntityManagerFactory("leadPU", props)
                .unwrap(Mutiny.SessionFactory.class);

        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Lead> query = cb.createQuery(Lead.class);

//        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
//        CriteriaQuery<Lead> select = criteriaBuilder.createQuery(Lead.class);
//        var rootZ = select.from(Lead.class);
//        Persistence.createEntityManagerFactory("leadPU")
//                    .unwrap(Mutiny.SessionFactory.class);


//        var persistenceProviderUnitInfo = new LeadPersistenceUnit();

//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("leadPU");

//        EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(persistenceProviderUnitInfo, props);
//        var em = emf.createEntityManager();
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        CriteriaQuery<Lead> criteriaQuery = criteriaBuilder.createQuery(Lead.class);
//        Root<Lead> rootZ = null;
//        rootZ = criteriaQuery.from(Lead.class);
//        var res = criteriaQuery.from(emf.getMetamodel().getEntities().stream().findFirst().get());

        var res = query.from(sessionFactory.getMetamodel().getEntities().stream().findFirst().get());

//        Root<Lead> resp = (Root<Lead>) criteriaQuery.from(emf.getMetamodel().getEntities().stream().findFirst().get());

        sessionFactory.withSession( sess -> {
            sess.createQuery("from Lead").executeUpdate();
            return null;
        }  );

//        EntityType<?> entidade = emf.getMetamodel().getEntities().stream().findFirst().get();
//        var type = entidade.getJavaType();
//        entidade.getName();

//        var entities = getEntities();
//        Class<Lead> clazz = (Class<Lead>) entities.get(0);
//        var classe = new Reflections("br.com.apirest.leadersofts.leadcapture.infrastructure.domain")
//                .getTypesAnnotatedWith(Entity.class).stream().findFirst().get();

//        Root<?> root = query.from(classe);
        List<Predicate> predicates = new ArrayList<>();

//        var type = criteriaQuery.from(emf.getMetamodel().getEntities().stream().findFirst().getClass());

//        var claz = em.getMetamodel().getEntities().stream().findFirst().get().getClass();
//        var novo = (Root<>) res;

//        prepareCriteriaQueryPredicates(predicates, cb,(Root<Lead>) res, filter);

//        if( !predicates.isEmpty())
//            query.where(predicates.stream().toArray( Predicate[]::new ));


        //TODO-Leandro uncomment
//        if (q != null && !q.trim().isEmpty()) {
//            query.where(
//                    cb.or(
//                            cb.like( cb.upper(root.get(Lead_.nome)), "%" + q.toUpperCase() + "%"  ),
//                            cb.like(root.get(Lead_.primeiroContato), "%" + q + "%")
//                    )
//            );
//
//            query.where(
//                    cb.like( cb.upper(root.get(Lead_.nome)), "%" + q.toUpperCase() + "%"  ),
//                    (cb.equal(cb.upper(root.get(Lead_.primeiroContato)), this.dateFilter.prepareData(filter.primeiroContato()))),
//                    (cb.equal(cb.upper(root.get(Lead_.ultimoContato)), this.dateFilter.prepareData(filter.ultimoContato()))),
//                    (cb.equal(cb.upper(root.get(Lead_.dataNascimento)), this.dateFilter.prepareData(filter.dataNascimento()))),
//                    (cb.like(cb.upper(root.get(Lead_.celular)), '%' + filter.celular().trim() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.celular2)), '%' + filter.celular2().trim() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.email)), '%' + filter.email().trim().toUpperCase() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.endereco)), '%' + filter.endereco().trim().toUpperCase() + '%')),
//                    (cb.equal(cb.upper(root.get(Lead_.uf)), filter.uf())),
//                    (cb.like(cb.upper(root.get(Lead_.cidade)), '%' + filter.cidade().trim().toUpperCase() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.carroAtual1)), '%' + filter.carroAtual1().trim().toUpperCase() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.carroAtual2)), '%' + filter.carroAtual2().trim().toUpperCase() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.carroAtual3)), '%' + filter.carroAtual3().trim().toUpperCase() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.carroInteresse1)), '%' + filter.carroInteresse1().trim().toUpperCase() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.carroInteresse2)), '%' + filter.carroInteresse2().trim().toUpperCase() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.carroInteresse3)), '%' + filter.carroInteresse3().trim().toUpperCase() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.vendedor)), '%' + filter.vendedor().trim().toUpperCase() + '%')),
//                    (cb.like(cb.upper(root.get(Lead_.observacoes)), '%' + filter.observacoes().trim().toUpperCase() + '%'))
//            );
//            //TODO-LEANDRO uncomment this block below or mabe remove it
//
////                        query.where(
////                    cb.like( cb.upper(root.get(Lead_.nome)), "%" + q.toUpperCase() + "%"  )),
//////                    ( cb.equal(cb.upper(root.get(Lead_.primeiroContato)), prepareData(filter.dataNascimento())))
////                    cb.and( this.dateFilter.matchPrimeiroContato0(filter,cb,query,root)),
////
////                    cb.and( this.dateFilter.matchUltimoContato(filter))
////                    cb.and( this.dateFilter.matchDataNascimento(filter))
////                    cb.and(this.contatoFilter.matchCelular(filter))
////                    cb.and(this.contatoFilter.matchCelular2(filter))
////                    cb.and(this.contatoFilter.matchEmail(filter))
////                    cb.and(this.contatoFilter.matchEndereco(filter))
////                    cb.and(this.contatoFilter.matchUnidadeFederativa(filter))
////                    cb.and(this.contatoFilter.matchCidade(filter))
////                    cb.and(this.vendaFilter.matchCarroAtual1(filter))
////                    cb.and(this.vendaFilter.matchCarroAtual2(filter))
////                    cb.and(this.vendaFilter.matchCarroAtual3(filter))
////                    cb.and(this.vendaFilter.matchCarroInteresse1(filter))
////                    cb.and(this.vendaFilter.matchCarroInteresse2(filter))
////                    cb.and(this.vendaFilter.matchCarroInteresse3(filter))
////                    cb.and(this.vendaFilter.matchVendedor(filter))
////                    cb.and(this.vendaFilter.matchObservacoes(filter))
////            )
//
//
//        }


//        sessionFactory.withStatelessSession( session ->
//                        session.createNativeQuery("SELECT * FROM lead")
//                                .getResultList()
//                                .invoke(leads -> leads.stream().forEach( l -> out.println("Resultado: " +  l )) )
////					.invoke(leads -> leads.stream().forEach(System.out::println))
//        ).await().indefinitely();

//        var sqlQuery = getCustomQuery(filter);
        var sqlQuery = "";

        var result = sessionFactory.withStatelessSession( session ->
                session.createNativeQuery(sqlQuery)
                        .getResultList()
                        .invoke(leads -> leads.stream().forEach(System.out::println))
        ).await().indefinitely();
        var field = (Object[]) result.get(0);
        var nome = field[0];

        //perform query
        return sessionFactory.withSession(session -> session.createQuery(query)
                .setFirstResult(0)
                .setMaxResults(limit)
                .getResultList());

    }


// TODO-LEANDRO  FUNCIONANDO
//    public LeadRepositoryCustomQueryImpl(LeadRepositoryContatoFilter contatoFilter, LeadRepositoryDateFilter dateFilter, LeadRepositoryVendaFilter vendaFilter) {
//        this.contatoFilter = contatoFilter;
//        this.dateFilter = dateFilter;
//        this.vendaFilter = vendaFilter;
//    }

//    public Uni<List<Lead>> findAll() {
//        CriteriaBuilder cb = this.sessionFactory.getCriteriaBuilder();
////        CriteriaBuilder cb = emf.getCriteriaBuilder();
//        CriteriaQuery<Lead> query = cb.createQuery(Lead.class);
//        Root<Lead> root = query.from(Lead.class);
//
//        return this.sessionFactory.withSession(session -> session.createQuery(query).getResultList());
//    }

    @Override
    public Flux<Lead> findWithFilter(LeadFilter filter) {

//        var leadsUni = this.findByKeywordFilter(filter,5,5);
//    var leadsList = this.findLeadWithFilter(filter);

    var leads = new ArrayList<Lead>();

    return Flux.fromIterable(leads);

    //        leadsUni
    //                .flatMap( leads -> {
    //                    leads
    //                            .stream()
    ////                            .flatMap( lead -> Flux.fromIterable(List.) )
    ////                            .flatMap( entity ->  )
    //                            .map(e -> collection.add(e))
    ////                            .flatMap( elem -> Flux.fromIterable(elem) )
    //                            .collect(Collectors.toList());
    //                    return null;
    //                });
    //
    //
    //        leadsUni
    //                .flatMap( leads -> {
    //                    leads
    //                            .stream()
    //                            .collect(Collectors.toList());
    //                    return
    //        });

    //        var leadsFlux =
    //        collection
    //                .stream()
    //                .map(Flux::just)
    //                .collect(Collectors.toSet())
    //                .stream()
    //                .flatMap(Flux::fromIterable)
    }

}
