package com.companyname.vending_machine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import com.companyname.vending_machine.model.dto.MoneyAmountDTO;


@SpringBootTest
@AutoConfigureTestDatabase
class VendingMachineWalletServiceTest {
    @Autowired
    private VendingMachineWalletService walletService;

    @Test
    void returnChangeShouldReturnCorrectValue() {
        MoneyAmountDTO money = new MoneyAmountDTO();
        money.setAmount(5000L);
        walletService.save(money);
        
        assertEquals(money.getAmount(), walletService.returnChange());
    }
}

