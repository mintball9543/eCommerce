package com.example.cms.domain.customer;

import lombok.Getter;

@Getter
public class ChangeBalanceForm {
    private String from;
    private String message;
    private Integer money;
}
