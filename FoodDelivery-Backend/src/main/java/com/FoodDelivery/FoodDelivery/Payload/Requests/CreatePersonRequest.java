package com.FoodDelivery.FoodDelivery.Payload.Requests;


/**
 * Request class for creating new person
 */
public class CreatePersonRequest {
    /**
     * Person name
     */
    private String name;
    /**
     * Person surname
     */
    private String surname;
    /**
     * Person phone number
     */
    private String phoneNumber;
    /**
     * Person email
     */
    private String email;
    /**
     * Office ID in which employee will be working, null for clients
     */
    private Long officeId;

    //Getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Long getOfficeId() {
        return officeId;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }
}
