package com.companyname.vending_machine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.companyname.vending_machine.model.dto.MoneyAmountDTO;
import com.companyname.vending_machine.service.VendingMachineWalletService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/wallet")
@Slf4j
public class WalletController {
    VendingMachineWalletService service;

    public WalletController(VendingMachineWalletService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public void addMoney(MoneyAmountDTO moneyAmountDTO) {
        log.debug("WalletController.addMoney is called.");
        service.save(moneyAmountDTO);
    }
    
    @PostMapping("/getChange")
    public ResponseEntity<Long> getChange() {
        log.debug("WalletController.getChange is called.");
        return ResponseEntity.ok(service.returnChange());
    }
}
