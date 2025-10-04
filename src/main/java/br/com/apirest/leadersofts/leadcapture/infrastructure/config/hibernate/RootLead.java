package br.com.apirest.leadersofts.leadcapture.infrastructure.config.hibernate;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.*;
import org.springframework.data.relational.core.query.Criteria;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class RootLead implements Root {


    public RootLead() {
        super();
    }

//    public Root<Lead> getRoot(Root<?> ) {
//        return this.
//    }


//    public void setRoot(Lead lead) {
//        return new Root<Lead>;
//    }

    public void setRoot(Object object) {

    }

    @Override
    public EntityType getModel() {
        return null;
    }

    @Override
    public Set<Join> getJoins() {
        return Set.of();
    }

    @Override
    public boolean isCorrelated() {
        return false;
    }

    @Override
    public From getCorrelationParent() {
        return null;
    }

    @Override
    public Join join(SingularAttribute singularAttribute) {
        return null;
    }

    @Override
    public Join join(SingularAttribute singularAttribute, JoinType joinType) {
        return null;
    }

    @Override
    public CollectionJoin join(CollectionAttribute collectionAttribute) {
        return null;
    }

    @Override
    public SetJoin join(SetAttribute setAttribute) {
        return null;
    }

    @Override
    public ListJoin join(ListAttribute listAttribute) {
        return null;
    }

    @Override
    public MapJoin join(MapAttribute mapAttribute) {
        return null;
    }

    @Override
    public CollectionJoin join(CollectionAttribute collectionAttribute, JoinType joinType) {
        return null;
    }

    @Override
    public SetJoin join(SetAttribute setAttribute, JoinType joinType) {
        return null;
    }

    @Override
    public ListJoin join(ListAttribute listAttribute, JoinType joinType) {
        return null;
    }

    @Override
    public MapJoin join(MapAttribute mapAttribute, JoinType joinType) {
        return null;
    }

    @Override
    public Join join(String s) {
        return null;
    }

    @Override
    public CollectionJoin joinCollection(String s) {
        return null;
    }

    @Override
    public SetJoin joinSet(String s) {
        return null;
    }

    @Override
    public ListJoin joinList(String s) {
        return null;
    }

    @Override
    public MapJoin joinMap(String s) {
        return null;
    }

    @Override
    public Join join(String s, JoinType joinType) {
        return null;
    }

    @Override
    public CollectionJoin joinCollection(String s, JoinType joinType) {
        return null;
    }

    @Override
    public SetJoin joinSet(String s, JoinType joinType) {
        return null;
    }

    @Override
    public ListJoin joinList(String s, JoinType joinType) {
        return null;
    }

    @Override
    public MapJoin joinMap(String s, JoinType joinType) {
        return null;
    }

    @Override
    public Path<?> getParentPath() {
        return null;
    }

    @Override
    public Path get(SingularAttribute singularAttribute) {
        return null;
    }

    @Override
    public Expression get(PluralAttribute pluralAttribute) {
        return null;
    }

    @Override
    public Expression get(MapAttribute mapAttribute) {
        return null;
    }

    @Override
    public Expression<Class> type() {
        return null;
    }

    @Override
    public Path get(String s) {
        return null;
    }

    @Override
    public Predicate isNull() {
        return null;
    }

    @Override
    public Predicate isNotNull() {
        return null;
    }

    @Override
    public Predicate in(Object... objects) {
        return null;
    }

    @Override
    public Predicate in(Expression[] expressions) {
        return null;
    }

    @Override
    public Predicate in(Collection collection) {
        return null;
    }

    @Override
    public Predicate in(Expression expression) {
        return null;
    }

    @Override
    public Expression as(Class aClass) {
        return null;
    }

    @Override
    public Set<Fetch> getFetches() {
        return Set.of();
    }

    @Override
    public Fetch fetch(SingularAttribute singularAttribute) {
        return null;
    }

    @Override
    public Fetch fetch(SingularAttribute singularAttribute, JoinType joinType) {
        return null;
    }

    @Override
    public Fetch fetch(PluralAttribute pluralAttribute) {
        return null;
    }

    @Override
    public Fetch fetch(PluralAttribute pluralAttribute, JoinType joinType) {
        return null;
    }

    @Override
    public Fetch fetch(String s) {
        return null;
    }

    @Override
    public Fetch fetch(String s, JoinType joinType) {
        return null;
    }

    @Override
    public Selection alias(String s) {
        return null;
    }

    @Override
    public boolean isCompoundSelection() {
        return false;
    }

    @Override
    public List<Selection<?>> getCompoundSelectionItems() {
        return List.of();
    }

    @Override
    public Class getJavaType() {
        return null;
    }

    @Override
    public String getAlias() {
        return "";
    }
}
