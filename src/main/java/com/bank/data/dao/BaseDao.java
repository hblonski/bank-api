package com.bank.data.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao {

    Serializable save(Object o);

    <T> T findById(Class<T> clazz, Serializable id);

    <T> List<T> find(Class<T> clazz, Map<String, Object> fieldValues);

    void delete(Object o);
}
