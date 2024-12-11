package com.oocl.ita.web.Service;

import com.oocl.ita.web.common.utils.SecurityUtils;
import com.oocl.ita.web.repository.TransactionRepository;
import com.oocl.ita.web.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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


}