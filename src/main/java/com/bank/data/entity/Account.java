package com.bank.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ACCOUNT", uniqueConstraints = @UniqueConstraint(columnNames = "number"))
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Number cannot be empty!")
    private String number;

    @Column(nullable = false)
    @NotNull(message = "Balance cannot be null!")
    private Double balance;

    @Column(nullable = false)
    @NotEmpty(message = "Bank cannot be empty!")
    private String bank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
