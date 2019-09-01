package com.bank.service;

import com.bank.BaseTest;
import com.bank.data.entity.Client;
import com.bank.data.repository.ClientRepository;
import com.bank.dto.ClientDTO;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest extends BaseTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        clientService = new ClientServiceImpl();
        return new ResourceConfig()
                .register(clientService)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(clientRepository).to(ClientRepository.class);
                    }
                });
    }

    @Test
    public void should_returnCreatedClientInfo_when_clientCreated() {
        ClientDTO clientDTO = mockClientDTO();
        ClientDtoToClientMapper mapper = new ClientDtoToClientMapper();
        Client client = mapper.map(clientDTO);
        when(clientRepository.save(any())).thenReturn(client);
        ClientDTO saved = clientService.save(clientDTO);
        assertNotNull(saved);
        assertEquals(client.getId(), saved.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void should_throwException_when_constraintsViolated() {
        ClientDTO clientDTO = mockClientDTO();
        when(clientRepository.save(any())).thenThrow(mock(ConstraintViolationException.class));
        clientService.save(clientDTO);
    }
}
