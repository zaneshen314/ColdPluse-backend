package com.oocl.ita.web.Service;

import com.oocl.ita.web.common.utils.SecurityUtils;
import com.oocl.ita.web.repository.TransactionRepository;
import com.oocl.ita.web.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private SecurityUtils securityUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_return_true_when_test_is_ok() {
        assertTrue(true);
    }


}