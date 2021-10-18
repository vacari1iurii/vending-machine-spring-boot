package com.companyname.vending_machine.model;

import java.util.Objects;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "cell", schema = "public")
public class VendingMachineCell {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cell_row", nullable = false)
    private String row;

    @Column(name = "cell_column", nullable = false)
    private Long column;

    @OneToOne
    private VendingMachineItem item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendingMachineCell that = (VendingMachineCell) o;
        return row.equals(that.row) && column.equals(that.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
