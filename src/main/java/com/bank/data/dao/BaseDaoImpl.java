package com.bank.data.dao;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;

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
    public <T> List<T> find(Class<T> clazz, String query) {
        return databaseSession.createQuery(query, clazz).getResultList();
    }

    @Override
    public void delete(Object o) {
        databaseSession.getTransaction().begin();
        databaseSession.remove(o);
        databaseSession.getTransaction().commit();
    }
}
