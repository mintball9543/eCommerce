package com.example.cms.application;

import com.example.cms.domain.SignInForm;
import com.example.cms.domain.model.Customer;
import com.example.cms.exception.CustomException;
import com.example.cms.service.CustomerService;
import com.example.config.JwtAuthenticationProvider;
import com.example.domain.common.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.cms.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form){
        // 1. 로그인 가능 여부
        Customer c = customerService.findValidCustomer(form.getEmail(),form.getPassword())
                .orElseThrow(()->new CustomException(LOGIN_CHECK_FAIL));

        return provider.createToken(c.getEmail(),c.getId(), UserType.CUSTOMER);
    }
}
