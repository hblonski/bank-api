package com.bank.data.entity;

import com.bank.data.value.BankProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ACCOUNT", uniqueConstraints = @UniqueConstraint(columnNames = "number"))
public class Account {

    public static final String ACCOUNT_ID_SEQUENCE = "ACCOUNT_SEQ";

    public Account() {
        zeroBalance();
    }

    public Account(
            Long id,
            @NotEmpty(message = "Number cannot be empty!") String number,
            Double balance,
            @NotEmpty(message = "Bank cannot be empty!") Integer bank
    ) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.bank = bank;
    }

    @Id
    @GeneratedValue(generator = ACCOUNT_ID_SEQUENCE, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = ACCOUNT_ID_SEQUENCE, sequenceName = ACCOUNT_ID_SEQUENCE, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Number cannot be empty!")
    private String number;

    private Double balance;

    @Column(nullable = false)
    @NotNull(message = "Bank cannot be null!")
    private Integer bank;

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

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public void zeroBalance() {
        this.balance = 0.0;
    }

    public boolean belongsToThisBank() {
        return BankProperties.BANK_CODE.equals(this.bank);
    }
}
