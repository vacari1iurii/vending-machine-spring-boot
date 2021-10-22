package com.companyname.vending_machine.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.companyname.vending_machine.model.dto.ImportDataDTO;
import com.companyname.vending_machine.service.VendingMachineService;
 

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final VendingMachineService service;

    public AdminController(VendingMachineService service) {
        this.service = service;
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImportDataDTO> importDataForVendingMachine(@RequestBody ImportDataDTO importData) {
        service.save(importData);
        return ResponseEntity.ok(importData);
    }
}