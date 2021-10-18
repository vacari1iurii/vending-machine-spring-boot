package com.companyname.vending_machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.vending_machine.model.VendingMachineCell;

public interface VendingMachineCellRepository extends JpaRepository<VendingMachineCell, Long> {

}
