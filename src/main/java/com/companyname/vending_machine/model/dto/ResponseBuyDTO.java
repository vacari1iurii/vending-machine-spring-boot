package com.companyname.vending_machine.model.dto;

import lombok.Data;

@Data
public class ResponseBuyDTO {
    private String balance;
    private Long remainingAmount;
}
