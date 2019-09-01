package com.bank.data.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {

    Serializable save(Object o);

    <T> List<T> find(Class<T> clazz, String query);

    void delete(Object o);
}
