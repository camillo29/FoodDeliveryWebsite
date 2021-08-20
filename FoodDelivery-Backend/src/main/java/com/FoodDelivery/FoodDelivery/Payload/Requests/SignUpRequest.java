package com.FoodDelivery.FoodDelivery.Payload.Requests;

/**
 * Request class for registering new account, combining user and person details
 */
public class SignUpRequest {
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
     * Email
     */
    private String email;
    /**
     * Password
     */
    private String password;
    /**
     * ID of office in which employee will be working, null for clients
     */
    private Long officeId;
    /**
     * Name of role user will be having
     */
    private String roleName;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

