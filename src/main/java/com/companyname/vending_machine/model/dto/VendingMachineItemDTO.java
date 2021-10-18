package com.companyname.vending_machine.model.dto;

import lombok.Data;

@Data
public class VendingMachineItemDTO {
    private String name;
    private Long amount;
    private String price;
}