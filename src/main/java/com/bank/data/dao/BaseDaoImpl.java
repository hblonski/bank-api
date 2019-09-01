package com.bank.data.dao;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BaseDaoImpl implements BaseDao {

    private static final Session databaseSession = new Configuration().configure().buildSessionFactory().openSession();

    @Override
    public Serializable save(Object o) {
        databaseSession.beginTransaction();
        Serializable id = databaseSession.save(o);
        databaseSession.getTransaction().commit();
        return id;
    }

    @Override
    public <T> T findById(Class<T> clazz, Serializable id) {
        return databaseSession.get(clazz,id);
    }

    @Override
    public <T> List<T> find(Class<T> clazz, Map<String, Object> fieldValues) {
        CriteriaBuilder builder = databaseSession.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(clazz);
        Root root = query.from(clazz);
        List<Predicate> predicates = Collections.emptyList();
        fieldValues.forEach((fieldName, fieldValue) -> {
            Path path = root.get(fieldName);
            builder.equal(path, fieldValue);
        });
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        return databaseSession.createQuery(query.select(root).where(builder.and(predicatesArray))).getResultList();
    }
}
