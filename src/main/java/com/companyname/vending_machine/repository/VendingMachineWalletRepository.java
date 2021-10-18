package com.companyname.vending_machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.companyname.vending_machine.model.VendingMachineWallet;

public interface VendingMachineWalletRepository extends JpaRepository<VendingMachineWallet, Long> {
    @Query(value = "SELECT * FROM wallet LIMIT 1", nativeQuery = true)
    VendingMachineWallet getFirst();

}
