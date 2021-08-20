package com.FoodDelivery.FoodDelivery.Payload.Requests;

/**
 * Request class for changing password
 */
public class ChangePasswordRequest {
    /**
     * Old password for comparing
     */
    private String oldPassword;
    /**
     * New password for updating
     */
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
