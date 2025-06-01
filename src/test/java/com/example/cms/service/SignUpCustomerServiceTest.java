package com.example.cms.service;

import com.example.cms.domain.SignUpForm;
import com.example.cms.domain.model.Customer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
public class SignUpCustomerServiceTest {
    
    @Autowired
    private SignUpCustomerService service;
    
    @Test
    void signUp(){
        SignUpForm form = SignUpForm.builder()
                .name("name")
                .birth(LocalDate.now())
                .email("abcd@gmail.com")
                .password("1")
                .phone("01000000000")
                .build();
        Customer c = service.signUp(form);

        assertNotNull(c.getId());
        assertNotNull(c.getCreatedAt());
    }

}
