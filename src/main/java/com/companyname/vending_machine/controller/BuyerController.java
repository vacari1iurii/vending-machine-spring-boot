package com.companyname.vending_machine.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.companyname.vending_machine.model.dto.BuyRequestDTO;
import com.companyname.vending_machine.model.dto.ResponseVendingMachineItemDTO;
import com.companyname.vending_machine.service.VendingMachineService;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {
    private final VendingMachineService service;

    public BuyerController(VendingMachineService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ResponseVendingMachineItemDTO>> showItemList() {
        return ResponseEntity.ok(service.getAllItems());
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestBody BuyRequestDTO requestDTO) {
        String result = service.buy(requestDTO);
        return ResponseEntity.ok(result);
    }
}
