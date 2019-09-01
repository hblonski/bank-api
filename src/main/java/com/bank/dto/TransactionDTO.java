package com.bank.dto;

import com.bank.data.value.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;

@XmlRootElement
public class TransactionDTO {

    public TransactionDTO() {
        // Empty
    }

    public TransactionDTO(
            Long id,
            TransactionType type,
            @NotNull(message = "Date must not be null!") Instant date,
            @NotNull(message = "Value must not be null!") @Min(value = 0L, message = "The value must be positive") Double value,
            @NotEmpty(message = "Account must not be empty!") String originAccount,
            @NotNull(message = "Bank must not be null!") Integer originAccountBank,
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

    private Long id;

    @JsonIgnore
    private TransactionType type;

    @NotNull(message = "Date must not be null!")
    private Instant date;

    @NotNull(message = "Value must not be null!")
    @Min(value = 0L, message = "The value must be positive")
    private Double value;

    @NotEmpty(message = "Account must not be empty!")
    private String originAccount;

    @NotNull(message = "Bank must not be null!")
    private Integer originAccountBank;

    private String destinationAccount;

    private Integer destinationAccountBank;

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

    @JsonIgnore
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
