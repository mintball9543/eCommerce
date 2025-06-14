package com.example.cms.order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ServletComponentScan
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableJpaAuditing
@SpringBootApplication
@EnableFeignClients
@ComponentScan("com.example")
public class OrderApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplicationMain.class,args);
    }
}
