package com.bank.data.dao;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> clazz, Map<String, Object> fieldValues) {
        CriteriaBuilder builder = databaseSession.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(clazz);
        Root root = query.from(clazz);
        List<Predicate> predicates = new ArrayList<>();
        fieldValues.forEach((fieldName, fieldValue) -> {
            Path path = root.get(fieldName);
            predicates.add(builder.equal(path, fieldValue));
        });
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        return databaseSession.createQuery(query.select(root).where(builder.and(predicatesArray))).getResultList();
    }

    @Override
    public BigInteger getNextSequenceValue(String sequenceName) {
        String query = "SELECT " + sequenceName + ".nextval FROM DUAL";
        Object nextValue = databaseSession.createNativeQuery(query).getSingleResult();
        return nextValue != null ? (BigInteger) nextValue : null;
    }
}
