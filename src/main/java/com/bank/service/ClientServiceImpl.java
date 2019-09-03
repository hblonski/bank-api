package com.bank.service;

import com.bank.data.entity.Client;
import com.bank.data.repository.ClientRepository;
import com.bank.dto.ClientDTO;
import com.bank.mapper.ClientDtoToClientMapper;
import com.bank.mapper.ClientToClientDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.validation.constraints.NotNull;

public class ClientServiceImpl implements ClientService {

    @Inject
    private ClientRepository clientRepository;

    @Override
    public ClientDTO save(@NotNull ClientDTO clientDTO) throws EntityExistsException {
        Client existent = clientRepository.findByDocumentNumber(clientDTO.getDocumentNumber());
        if (existent != null) {
            throw new EntityExistsException("Client with document number already exists.");
        }
        Client client = new ClientDtoToClientMapper().map(clientDTO);
        return new ClientToClientDtoMapper().map(clientRepository.save(client));
    }
}
