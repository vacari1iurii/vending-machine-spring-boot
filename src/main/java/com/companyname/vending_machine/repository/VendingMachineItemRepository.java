package com.companyname.vending_machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.vending_machine.model.VendingMachineItem;

public interface VendingMachineItemRepository extends JpaRepository<VendingMachineItem, Long> {

}
