package com.companyname.vending_machine.model;

import javax.persistence.*;
import java.util.Objects;
import lombok.Data;


@Data
@Entity
@Table(name = "item", schema = "public")
public class VendingMachineItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long amount;
    private Long price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendingMachineItem item = (VendingMachineItem) o;
        return name.equals(item.name) && amount.equals(item.amount) && price.equals(item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, price);
    }
    
    public VendingMachineItem buyOne() {
        amount--;
        return this;
    }
}