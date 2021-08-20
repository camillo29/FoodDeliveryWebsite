package com.FoodDelivery.FoodDelivery.Model;

import javax.persistence.*;

@Entity
@Table(name = "People")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "eMail", nullable = false)
    private String eMail;

    @Column(name = "fired", nullable = true)
    private Boolean fired;

    @ManyToOne
    private Office office;

    public Person(){}
    public Person(String name, String surname, String phoneNumber, String eMail, Office office){
                this.name = name;
                this.surname = surname;
                this.phoneNumber = phoneNumber;
                this.eMail = eMail;
                this.office = office;
                this.fired = false;
    }
    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public void setFired(Boolean fired) {
        this.fired = fired;
    }

    //Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEMail() {
        return eMail;
    }

    public Office getOffice() {
        return office;
    }

    public Boolean getFired() {
        return fired;
    }
}
