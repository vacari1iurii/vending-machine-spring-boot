package com.companyname.vending_machine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companyname.vending_machine.model.VendingMachineCell;
import com.companyname.vending_machine.model.VendingMachineItem;
import com.companyname.vending_machine.model.dto.ImportDataDTO;
import com.companyname.vending_machine.model.dto.VendingMachineItemDTO;
import com.companyname.vending_machine.repository.VendingMachineCellRepository;
import com.companyname.vending_machine.repository.VendingMachineItemRepository;

@Service
public class VendingMachineService {
    private final VendingMachineCellRepository cellRepo;
    private final VendingMachineItemRepository itemRepo;

    public VendingMachineService(VendingMachineCellRepository cellRepo, VendingMachineItemRepository itemRepo) {
        this.cellRepo = cellRepo;
        this.itemRepo = itemRepo;
    }

    @Transactional
    public void save(ImportDataDTO importData) {
        List<VendingMachineItem> savedItems = new ArrayList<>();

        for (VendingMachineItemDTO itemDTO : importData.getItems()) {
            VendingMachineItem item = new VendingMachineItem();
            item.setAmount(itemDTO.getAmount());
            item.setName(itemDTO.getName());
            item.setPrice(Long.parseLong(itemDTO.getPrice().replaceAll("\\$","").replaceAll("\\.", "")));

            savedItems.add(itemRepo.save(item));
        }

        for (long i = 0; i < importData.getConfig().getRows(); i++) {
            for (long j = 1; j <= Long.parseLong(importData.getConfig().getColumns()); j++) {
                VendingMachineCell cell = new VendingMachineCell();

                cell.setRow(String.valueOf((char)('A' + i)));
                cell.setColumn(j);

                if(!savedItems.isEmpty()) {
                    cell.setItem(savedItems.get(0));
                    savedItems.remove(0);
                }

                cellRepo.save(cell);
            }
        }
    }
}