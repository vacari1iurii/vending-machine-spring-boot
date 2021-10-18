package com.companyname.vending_machine.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ImportDataDTO {
    private ImportVendingMachineConfigDTO config;
    private List<VendingMachineItemDTO> items;
}