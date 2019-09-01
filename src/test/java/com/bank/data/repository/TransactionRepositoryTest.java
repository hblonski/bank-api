package com.bank.data.repository;

import com.bank.BaseTest;
import com.bank.data.dao.BaseDao;
import com.bank.data.entity.Transaction;
import com.bank.mapper.TransactionDtoToTransactionMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRepositoryTest extends BaseTest {

    @Mock
    private BaseDao baseDao;

    private TransactionRepository transactionRepository;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        transactionRepository = new TransactionRepositoryImpl();
        return new ResourceConfig()
                .register(transactionRepository)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(baseDao).to(BaseDao.class);
                    }
                });
    }

    @Test
    public void should_returnTransaction_when_transactionSaved() {
        Transaction transaction = new TransactionDtoToTransactionMapper().map(mockTransactionDTO());
        Long id = 1L;
        when(baseDao.save(any())).thenReturn(id);
        Transaction saved = transactionRepository.save(transaction);
        assertEquals(id, saved.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void should_throwException_when_validationError() {
        Transaction transaction = new TransactionDtoToTransactionMapper().map(mockTransactionDTO());
        String message = "violation";
        ConstraintViolationException exception = mockViolationException(message);
        when(baseDao.save(any())).thenThrow(exception);
        transactionRepository.save(transaction);
    }
}
