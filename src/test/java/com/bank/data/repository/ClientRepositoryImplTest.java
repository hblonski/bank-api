package com.bank.data.repository;

import com.bank.BaseTest;
import com.bank.data.dao.BaseDao;
import com.bank.data.entity.Client;
import com.bank.mapper.ClientDtoToClientMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Application;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientRepositoryImplTest extends BaseTest {

    @Mock
    private BaseDao baseDao;

    private ClientRepository clientRepository;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        clientRepository = new ClientRepositoryImpl();
        return new ResourceConfig()
                .register(clientRepository)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(baseDao).to(BaseDao.class);
                    }
                });
    }

    @Test
    public void should_returnCreatedClientInfo_when_clientCreated() {
        Client client = new ClientDtoToClientMapper().map(mockClientDTO());
        client.setId(null);
        Long id = 1L;
        when(baseDao.save(any())).thenReturn(id);
        Client saved = clientRepository.save(client);
        assertEquals(id, saved.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void should_throwException_when_validationError() {
        Client client = new ClientDtoToClientMapper().map(mockClientDTO());
        String message = "violation";
        ConstraintViolationException exception = mockViolationException(message);
        when(baseDao.save(any())).thenThrow(exception);
        clientRepository.save(client);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_findByDocumentNumber_when_clientExists() {
        List<Client> clients = List.of(mock(Client.class));
        when(baseDao.find(any(Class.class), any())).thenReturn(clients);
        Client result = clientRepository.findByDocumentNumber("any");
        assertNotNull(result);
    }

    @Test
    public void should_returnNull_when_clientDoesNotExist() {
        Client result = clientRepository.findByDocumentNumber("any");
        assertNull(result);
    }
}
