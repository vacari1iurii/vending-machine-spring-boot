package com.companyname.vending_machine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.vending_machine.model.VendingMachineCell;

public interface VendingMachineCellRepository extends JpaRepository<VendingMachineCell, Long> {
    List<VendingMachineCell> getAllByItemNotNull();
    
    VendingMachineCell findByRowAndColumn(String row, Long column);
}
