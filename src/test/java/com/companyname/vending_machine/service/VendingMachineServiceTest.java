package com.companyname.vending_machine.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.companyname.vending_machine.model.VendingMachineCell;
import com.companyname.vending_machine.model.dto.BuyRequestDTO;
import com.companyname.vending_machine.model.dto.ImportDataDTO;
import com.companyname.vending_machine.model.dto.ImportVendingMachineConfigDTO;
import com.companyname.vending_machine.model.dto.MoneyAmountDTO;
import com.companyname.vending_machine.model.dto.ResponseBuyDTO;
import com.companyname.vending_machine.model.dto.VendingMachineItemDTO;
import com.companyname.vending_machine.repository.VendingMachineCellRepository;

@SpringBootTest
@AutoConfigureTestDatabase
class VendingMachineServiceTest {
    @Autowired
    private VendingMachineService machineService;
    @Autowired
    private VendingMachineWalletService walletService;
    @Autowired
    private VendingMachineCellRepository cellRepo;
    
    @Test
    void testSave() {
        ImportDataDTO dto = new ImportDataDTO();
        ImportVendingMachineConfigDTO config = new ImportVendingMachineConfigDTO();
        List<VendingMachineItemDTO> items = new ArrayList<>();
        VendingMachineItemDTO item1 = new VendingMachineItemDTO();
        VendingMachineItemDTO item2 = new VendingMachineItemDTO();
        item1.setAmount(10L);
        item1.setName("TestItem");
        item1.setPrice("$1.35");
        item2.setAmount(50L);
        item2.setName("TestItem2");
        item2.setPrice("$2.40");
        items.add(item1);
        items.add(item2);
        config.setRows(4L);
        config.setColumns("8");
        dto.setConfig(config);
        dto.setItems(items);
        
        machineService.save(dto);
        Optional<VendingMachineCell> cell = cellRepo.getAllByItemNotNull()
                .stream().filter(c -> c.getItem().getName().equals(item1.getName()))
                .findFirst();
        
        assertTrue(cell.isPresent());   
    }

    @Test
    void testBuy() {
        final Long BOUGHT_ITEM = 1L;
        
        ImportDataDTO data = new ImportDataDTO();
        ImportVendingMachineConfigDTO config = new ImportVendingMachineConfigDTO();
        List<VendingMachineItemDTO> items = new ArrayList<>();
        VendingMachineItemDTO item = new VendingMachineItemDTO();
        MoneyAmountDTO money = new MoneyAmountDTO();
        
        money.setAmount(5000L);
        walletService.save(money);
        
        item.setAmount(10L);
        item.setName("TestItem");
        item.setPrice("$1.35");
        items.add(item);

        config.setRows(4L);
        config.setColumns("8");
        
        data.setConfig(config);
        data.setItems(items);
        
        machineService.save(data);
        
        BuyRequestDTO requestDTO = new BuyRequestDTO();
        requestDTO.setColumn(1L);
        requestDTO.setRow("A");
        ResponseBuyDTO response = machineService.buy(requestDTO);

        assertEquals(item.getAmount() - BOUGHT_ITEM, response.getRemainingAmount());
    }
}
