package com.bank.data.repository;

import com.bank.data.dao.BaseDao;
import com.bank.data.entity.Client;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ClientRepositoryImpl implements ClientRepository {

    @Inject
    private BaseDao baseDao;

    @Override
    public Client save(@NotNull Client client) {
        Serializable id = baseDao.save(client);
        client.setId((Long) id);
        return client;
    }

    @Override
    public Client findById(@NotNull Long id) {
        return baseDao.findById(Client.class, id);
    }
}
