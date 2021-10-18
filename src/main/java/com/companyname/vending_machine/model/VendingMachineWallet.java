package com.companyname.vending_machine.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "wallet", schema = "public")
public class VendingMachineWallet {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    
    public VendingMachineWallet substractPrice(Long price) {
        amount -= price;
        return this;
    }
}
