package com.example.cms.application;

import com.example.cms.client.MailgunClient;
import com.example.cms.client.mailgurn.SendMailForm;
import com.example.cms.domain.SignUpForm;
import com.example.cms.domain.model.Customer;
import com.example.cms.domain.repository.CustomerRepository;
import com.example.cms.exception.CustomException;
import com.example.cms.exception.ErrorCode;
import com.example.cms.service.SignUpCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpApplication {
   private final MailgunClient mailgunClient;
   private final CustomerRepository customerRepository;
    private final SignUpCustomerService signUpCustomerService;

    public String customerSignUp(SignUpForm form){

       if(signUpCustomerService.isEmailExist(form.getEmail())){
           throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
       }else{
           Customer c = signUpCustomerService.signUp(form);
           LocalDateTime now = LocalDateTime.now();

           String code = getRandomCode();
           SendMailForm sendMailForm = SendMailForm.builder()
                   .from("tester@dannymytester.com")
                   .to(form.getEmail())
                   .subject("Verification Email")
                   .text(getVerificationEmailBody(form.getEmail(), form.getName(), getRandomCode()))
                   .build();

           log.info("Send email result : "+mailgunClient.sendEmail(sendMailForm).getBody());

           mailgunClient.sendEmail(sendMailForm);
           signUpCustomerService.changeCustomerValidateEmail(c.getId(),code);
           return "회원 가입에 성공하였습니다.";
       }
   }

   private String getRandomCode() {
        return RandomStringUtils.random(10,true,true);
   }

   private String getVerificationEmailBody(String email, String name, String code){
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verification.\n\n")
                .append("http://localhost:8080/customer/signup/verify?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
   }

    public void customerVerify(String email,String code){
        signUpCustomerService.verifyEmail(email,code);
    }
}
