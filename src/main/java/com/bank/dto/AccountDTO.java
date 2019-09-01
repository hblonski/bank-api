package com.bank.dto;

import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccountDTO {

    public AccountDTO() {
        // Empty
    }

    public AccountDTO(
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

    private Long id;

    @NotEmpty(message = "Number cannot be empty!")
    private String number;

    private Double balance;

    @NotEmpty(message = "Bank cannot be empty!")
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
}
