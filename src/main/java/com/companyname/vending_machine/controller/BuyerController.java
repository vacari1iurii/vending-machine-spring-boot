package com.companyname.vending_machine.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.companyname.vending_machine.model.dto.BuyRequestDTO;
import com.companyname.vending_machine.model.dto.ResponseBuyDTO;
import com.companyname.vending_machine.model.dto.ResponseVendingMachineItemDTO;
import com.companyname.vending_machine.service.VendingMachineService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/buyer")
@Slf4j
public class BuyerController {
    private final VendingMachineService service;

    public BuyerController(VendingMachineService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ResponseVendingMachineItemDTO>> showItemList() {
        log.debug("BuyerController.showItemList is called.");
        return ResponseEntity.ok(service.getAllItems());
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestBody BuyRequestDTO requestDTO) {
        log.debug("BuyerController.buy is called.");
        ResponseBuyDTO result = service.buy(requestDTO);
        return ResponseEntity.ok(String.format("Purchase completed successfully. Your balance is %s.", result.getBalance()));
    }
}
