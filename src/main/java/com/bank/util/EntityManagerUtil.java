package com.bank.util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public final class EntityManagerUtil {

    private EntityManagerUtil() {
        // Empty
    }

    public static EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("entityManager").createEntityManager();
    }
}
