package com.companyname.vending_machine.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseVendingMachineItemDTO {
    private Long amount;
    private String name;
    private String price;
    private String place;
}
