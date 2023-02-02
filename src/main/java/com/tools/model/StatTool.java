package com.tools.model;

import com.tools.controller.main.Main;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class StatTool {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne
    private Tool tool;

    private int quantity;
    private double income;

    public StatTool() {
        this.quantity = 0;
        this.income = 0;
    }

    public void addIncome(int quantity, double sum) {
        this.quantity += quantity;
        this.income += Main.round(sum, 2);
    }
}
