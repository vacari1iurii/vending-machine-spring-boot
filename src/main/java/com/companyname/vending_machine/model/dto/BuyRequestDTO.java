package com.companyname.vending_machine.model.dto;

import lombok.Data;

@Data
public class BuyRequestDTO {
    private String row;
    private Long column;
}
