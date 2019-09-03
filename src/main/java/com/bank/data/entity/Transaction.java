package com.bank.data.entity;

import com.bank.data.value.TransactionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    public Transaction() {
        // Empty
    }

    public Transaction(
            Long id,
            TransactionType type,
            @NotNull(message = "Date must not be null!") Instant date,
            @NotNull(message = "Value must not be null!") Double value,
            String originAccount,
            Integer originAccountBank,
            String destinationAccount,
            Integer destinationAccountBank,
            String description
    ) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.value = value;
        this.originAccount = originAccount;
        this.originAccountBank = originAccountBank;
        this.destinationAccount = destinationAccount;
        this.destinationAccountBank = destinationAccountBank;
        this.description = description;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false)
    @NotNull(message = "Date must not be null!")
    private Instant date;

    @Column(nullable = false)
    @NotNull(message = "Value must not be null!")
    @Min(value = 0L, message = "The value must be positive")
    private Double value;

    @Column
    private String originAccount;

    @Column
    private Integer originAccountBank;

    @Column
    private String destinationAccount;

    @Column
    private Integer destinationAccountBank;

    @Column
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(String originAccount) {
        this.originAccount = originAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOriginAccountBank() {
        return originAccountBank;
    }

    public void setOriginAccountBank(Integer originAccountBank) {
        this.originAccountBank = originAccountBank;
    }

    public Integer getDestinationAccountBank() {
        return destinationAccountBank;
    }

    public void setDestinationAccountBank(Integer destinationAccountBank) {
        this.destinationAccountBank = destinationAccountBank;
    }
}
