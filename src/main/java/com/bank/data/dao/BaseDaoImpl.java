package com.bank.data.dao;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

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
    public void delete(Object o) {
        databaseSession.getTransaction().begin();
        databaseSession.remove(o);
        databaseSession.getTransaction().commit();
    }
}
