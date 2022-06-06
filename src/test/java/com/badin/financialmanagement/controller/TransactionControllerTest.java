package com.badin.financialmanagement.controller;

import com.badin.financialmanagement.model.Transaction;
import com.badin.financialmanagement.repository.AccountRepository;
import com.badin.financialmanagement.repository.CategoryRepository;
import com.badin.financialmanagement.service.TransactionService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransactionControllerTest {

    @Mock
    private Authentication auth;

    @Mock
    private SecurityContext securityContext;

    @Autowired
    private WebApplicationContext wac;

    @Mock
    TransactionService transactionService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    AccountRepository accountRepository;

    private MockMvc mockMvc;

    @InjectMocks
    TransactionController transactionController;


    @Before
    public void createMocks() {
        MockitoAnnotations.initMocks(this);
        when(auth.getCredentials()).thenReturn("mockedPassword");

        System.out.println("1111");

    }

    @BeforeEach
    public void initSecurityContext() {
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("bank");
        SecurityContextHolder.setContext(securityContext);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity()).build();
        System.out.println("2222");

    }

    @Test
    public void getTransactionList() throws Exception {
        when(auth.getPrincipal()).thenReturn("bank");
        when(auth.getName()).thenReturn("bank");
        SecurityContextHolder.getContext().setAuthentication(auth);
        List<Transaction> list = new ArrayList<>();
        when(transactionService.findByCondition(any(),any(),any(),any(),any())).thenReturn(list);
        MvcResult result = this.mockMvc.perform(get("/transactions/").with(user("bank").password("password").roles("USER","ADMIN"))).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        this.mockMvc.perform(get("/transactions/").with(user("bank").password("password").roles("USER","ADMIN")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("transactions", list));
    }

    @Test
    @WithMockUser(username = "bank", password = "password", roles = "USER")
    public void getAddPage() throws Exception {
        this.mockMvc.perform(get("/transactions/add-form").with(user("bank").password("password").roles("USER","ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("transaction/form"));
    }

}