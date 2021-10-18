package com.companyname.vending_machine.service;

import org.springframework.stereotype.Service;

import com.companyname.vending_machine.model.VendingMachineWallet;
import com.companyname.vending_machine.model.dto.MoneyAmountDTO;
import com.companyname.vending_machine.repository.VendingMachineWalletRepository;

@Service
public class VendingMachineWalletService {
    private final VendingMachineWalletRepository walletRepo;
    

    public VendingMachineWalletService(VendingMachineWalletRepository walletRepo) {
        this.walletRepo = walletRepo;
    }

    public void save(MoneyAmountDTO moneyAmountDTO) {
        VendingMachineWallet wallet = walletRepo.getFirst();
        wallet.setAmount(wallet.getAmount() + moneyAmountDTO.getAmount());
        walletRepo.save(wallet);
    }
    
    public Long returnChange() {
        Long change = walletRepo.getFirst().getAmount();
        
        walletRepo.save(walletRepo.getFirst().substractPrice(change));
        return change;
    }
}
