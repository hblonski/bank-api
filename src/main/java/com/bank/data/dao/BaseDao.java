package com.bank.data.dao;

import java.io.Serializable;

public interface BaseDao {

    Serializable save(Object o);

    <T> T findById(Class<T> clazz, Serializable id);

    void delete(Object o);
}
