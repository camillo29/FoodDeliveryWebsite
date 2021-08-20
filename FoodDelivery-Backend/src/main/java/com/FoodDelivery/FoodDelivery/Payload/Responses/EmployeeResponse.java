package com.FoodDelivery.FoodDelivery.Payload.Responses;

import com.FoodDelivery.FoodDelivery.Model.User;

/**
 * Response class for employee, containing combined information from office, person and user
 */
public class EmployeeResponse {
    /**
     * ID
     */
    private long id;
    /**
     * Person name
     */
    private String name;
    /**
     * Person surname
     */
    private String surname;
    /**
     * Person email, username
     */
    private String email;
    /**
     * person phoneNumber
     */
    private String phoneNumber;
    /**
     * Office city
     */
    private String city;
    /**
     * Office street
     */
    private String street;
    /**
     * Office post code
     */
    private String postCode;
    /**
     * Fired flag
     */
    private Boolean fired;

    public EmployeeResponse(User user){
        id = user.getId();
        name = user.getPerson().getName();
        surname = user.getPerson().getSurname();
        email = user.getUsername();
        phoneNumber = user.getPerson().getPhoneNumber();
        city = user.getPerson().getOffice().getCity();
        street = user.getPerson().getOffice().getStreet();
        postCode = user.getPerson().getOffice().getPostCode();
        fired = user.getPerson().getFired();
    }
    public EmployeeResponse(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Boolean getFired() {
        return fired;
    }

    public void setFired(Boolean fired) {
        this.fired = fired;
    }
}
