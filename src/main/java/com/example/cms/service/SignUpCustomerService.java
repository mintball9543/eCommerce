package com.example.cms.service;

import com.example.cms.domain.SignUpForm;
import com.example.cms.domain.model.Customer;
import com.example.cms.domain.repository.CustomerRepository;
import com.example.cms.exception.CustomException;
import com.example.cms.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static com.example.cms.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {
    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form){
        return customerRepository.save(Customer.from(form));
    }

    public boolean isEmailExist(String email){
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            customer .setVerificationCode(verificationCode);
            customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return customer.getVerifyExpiredAt();
        }
        throw new CustomException(NOT_FOUND_USER);
    }


    @Transactional
    public void verifyEmail(String email,String code){
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(NOT_FOUND_USER));
        if(customer.isVerify()){
            throw new CustomException(ALREADY_VERIFY);
        } else if(!customer.getVerificationCode().equals(code)){
            throw new CustomException(WRONG_VERIFICATION);
        } else if(customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(EXPIRE_CODE);
        }
        customer.setVerify(true);
    }

}
