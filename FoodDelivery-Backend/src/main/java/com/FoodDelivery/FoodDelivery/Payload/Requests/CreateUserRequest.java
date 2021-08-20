package com.FoodDelivery.FoodDelivery.Payload.Requests;

/**
 * Request class for creating user
 */
public class CreateUserRequest {
    /**
     * Email
     */
    private String email;
    /**
     * Password
     */
    private String password;
    /**
     * Id of person having this account
     */
    private Long personId;
    /**
     * Id of role this user will be having
     */
    private Long roleId;

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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
