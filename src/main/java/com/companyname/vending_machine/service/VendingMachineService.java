package com.companyname.vending_machine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companyname.vending_machine.model.VendingMachineCell;
import com.companyname.vending_machine.model.VendingMachineItem;
import com.companyname.vending_machine.model.dto.BuyRequestDTO;
import com.companyname.vending_machine.model.dto.ImportDataDTO;
import com.companyname.vending_machine.model.dto.ResponseVendingMachineItemDTO;
import com.companyname.vending_machine.model.dto.VendingMachineItemDTO;
import com.companyname.vending_machine.repository.VendingMachineCellRepository;
import com.companyname.vending_machine.repository.VendingMachineItemRepository;
import com.companyname.vending_machine.repository.VendingMachineWalletRepository;

@Service
public class VendingMachineService {
    private final VendingMachineCellRepository cellRepo;
    private final VendingMachineItemRepository itemRepo;
    private final VendingMachineWalletRepository walletRepo;

    public VendingMachineService(VendingMachineCellRepository cellRepo, VendingMachineItemRepository itemRepo, 
            VendingMachineWalletRepository walletRepo) {
        this.cellRepo = cellRepo;
        this.itemRepo = itemRepo;
        this.walletRepo = walletRepo;
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

    public List<ResponseVendingMachineItemDTO> getAllItems() {
        List<ResponseVendingMachineItemDTO> list = new ArrayList<>();

        for (VendingMachineCell cell : cellRepo.getAllByItemNotNull()) {
            long price = cell.getItem().getPrice();
            long dollars = price / 100;
            long cents = price % 100;
            ResponseVendingMachineItemDTO itemDTO = ResponseVendingMachineItemDTO.builder()
                    .amount(cell.getItem().getAmount())
                    .name(cell.getItem().getName())
                    .place(cell.getRow() + cell.getColumn())
                    .price("$" + dollars + "." + cents)
                    .build();
            list.add(itemDTO);
        }
        return list;
    }
       
    @Transactional
    public void buy(BuyRequestDTO requestDTO) {
        VendingMachineCell cell = cellRepo.findByRowAndColumn(requestDTO.getRow(), requestDTO.getColumn());
        checkRequestValue(cell);
        checkWalletAmountEnough(cell.getItem());
        buyItem(cell.getItem());
        
    }
      
    private void checkRequestValue(VendingMachineCell cell) throws IllegalArgumentException {
        if (cell == null || cell.getItem() == null || cell.getItem().getAmount() == 0) {
            throw new IllegalArgumentException();
        } 
    }
    
    private void checkWalletAmountEnough(VendingMachineItem item) {
        if (item.getAmount() <= 0 || item.getPrice() > walletRepo.getFirst().getAmount()) {
            throw new IllegalArgumentException();
        }
    }
    
    private void buyItem(VendingMachineItem item) {
        itemRepo.save(item.buyOne());
        walletRepo.save(walletRepo.getFirst().substractPrice(item.getPrice()));    
    }
}
