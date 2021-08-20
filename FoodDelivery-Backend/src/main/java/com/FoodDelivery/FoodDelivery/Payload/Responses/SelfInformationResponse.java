package com.FoodDelivery.FoodDelivery.Payload.Responses;

import com.FoodDelivery.FoodDelivery.Model.Office;

/**
 * Response class for obtaining user's own information
 */
public class SelfInformationResponse {
    /**
     * Name, surname, phone number and email
     */
    private String name, surname, phoneNumber, email;
    /**
     * Office in which employee works, null for client
     */
    private Office office;

    public SelfInformationResponse(){}
    public SelfInformationResponse(String name, String surname, String phoneNumber, String email, Office office){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.office = office;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
