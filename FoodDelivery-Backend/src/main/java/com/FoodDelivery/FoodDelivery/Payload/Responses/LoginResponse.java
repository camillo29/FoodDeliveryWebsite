package com.FoodDelivery.FoodDelivery.Payload.Responses;

/**
 * Response class for logging in
 */
public class LoginResponse {
    /**
     * Jason Web Token
     */
    private final String token;
    /**
     * User email
     */
    private final String email;

    public LoginResponse(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return token;
    }
    public String getEmail(){
        return email;
    }
}
