package com.bank.dto;

import com.bank.data.entity.Account;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientDTO {

    public ClientDTO() {
        // Empty
    }

    public ClientDTO(
            Long id,
            @NotNull String name,
            @NotNull String documentNumber,
            @NotNull String address,
            @NotNull String city,
            @NotNull String state,
            @NotNull String country,
            String profession,
            String phone,
            Account account
    ) {
        this.id = id;
        this.name = name;
        this.documentNumber = documentNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.profession = profession;
        this.phone = phone;
        this.account = account;
    }

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String documentNumber;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String country;

    private String profession;

    private String phone;

    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
