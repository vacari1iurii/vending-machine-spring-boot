package com.companyname.vending_machine.seed;

import javax.transaction.Transactional;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.companyname.vending_machine.model.VendingMachineWallet;
import com.companyname.vending_machine.repository.VendingMachineWalletRepository;

@Component
public class DatabaseSeeder {
    private VendingMachineWalletRepository walletRepo;

    public DatabaseSeeder(VendingMachineWalletRepository walletRepo) {
        this.walletRepo = walletRepo;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedWalletTable();
    }

    @Transactional
    public void seedWalletTable() {
        VendingMachineWallet wallet = new VendingMachineWallet();
        wallet.setAmount(0L);
        walletRepo.save(wallet);
    }
}
