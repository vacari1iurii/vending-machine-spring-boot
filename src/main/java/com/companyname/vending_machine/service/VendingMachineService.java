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
        List<VendingMachineItem> items = getItemsFromImportDataDTO(importData);

        putItemsIntoVendingMachine(importData, items);
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
      
    private void checkRequestValue(VendingMachineCell cell) {
        if (cell == null || cell.getItem() == null || cell.getItem().getAmount() == 0) {
            throw new IllegalArgumentException("The selected position does not exist or it is empty. "
                    + "Please check and select once again.");
        } 
    }
    
    private void checkWalletAmountEnough(VendingMachineItem item) {
        if (item.getAmount() <= 0 || item.getPrice() > walletRepo.getFirst().getAmount()) {
            throw new IllegalArgumentException("Insufficient funds to purchase selected item. "
                    + "Please add more funds or choose another item.");
        }
    }
    
    private void buyItem(VendingMachineItem item) {
        itemRepo.save(item.buyOne());
        walletRepo.save(walletRepo.getFirst().substractPrice(item.getPrice()));    
    }
    
    private List<VendingMachineItem> getItemsFromImportDataDTO(ImportDataDTO importData) {
        List<VendingMachineItem> items = new ArrayList<>();
        
        for (VendingMachineItemDTO itemDTO : importData.getItems()) {
            VendingMachineItem item = new VendingMachineItem();
            item.setAmount(itemDTO.getAmount());
            item.setName(itemDTO.getName());
            item.setPrice(Long.parseLong(itemDTO.getPrice().replaceAll("\\$","").replaceAll("\\.", "")));

            items.add(itemRepo.save(item));
        }
        
        return items;
    }
    
    private void putItemsIntoVendingMachine(ImportDataDTO importData, List<VendingMachineItem> items) {
        for (long i = 0; i < importData.getConfig().getRows(); i++) {
            for (long j = 1; j <= Long.parseLong(importData.getConfig().getColumns()); j++) {
                VendingMachineCell cell = new VendingMachineCell();

                cell.setRow(String.valueOf((char)('A' + i)));
                cell.setColumn(j);

                if(!items.isEmpty()) {
                    cell.setItem(items.get(0));
                    items.remove(0);
                }

                cellRepo.save(cell);
            }
        }  
    }
}
