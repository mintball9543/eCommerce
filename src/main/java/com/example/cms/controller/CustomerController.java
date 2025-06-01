package com.example.cms.controller;

import com.example.cms.domain.customer.CustomerDto;
import com.example.cms.domain.model.Customer;
import com.example.cms.exception.CustomException;
import com.example.cms.service.CustomerBalanceService;
import com.example.cms.service.CustomerService;
import com.example.config.JwtAuthenticationProvider;
import com.example.domain.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.cms.exception.ErrorCode.NOT_FOUND_USER;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final CustomerBalanceService customerBalanceService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token){
        UserVo vo = provider.getUserVo(token);
        Customer c = customerService.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()->new CustomException(NOT_FOUND_USER));
        return ResponseEntity.ok(CustomerDto.from(c));
    }

}
