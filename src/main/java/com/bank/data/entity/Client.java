package com.bank.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "CLIENT", uniqueConstraints = @UniqueConstraint(columnNames = "documentNumber"))
public class Client {

    public Client() {
        // Empty
    }

    public Client(
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

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "Document value cannot be empty!")
    private String documentNumber;

    @Column(nullable = false)
    @NotEmpty(message = "Address cannot be empty!")
    private String address;

    @Column(nullable = false)
    @NotEmpty(message = "City cannot be empty!")
    private String city;

    @Column(nullable = false)
    @NotEmpty(message = "State cannot be empty!")
    private String state;

    @Column(nullable = false)
    @NotEmpty(message = "Country cannot be empty!")
    private String country;

    @Column
    private String profession;

    @Column
    private String phone;

    @OneToOne
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
