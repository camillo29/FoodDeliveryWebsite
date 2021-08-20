package com.FoodDelivery.FoodDelivery.Payload.Requests;

/**
 * Request class for changing employee "fired" flag status
 */
public class ChangeEmployeeStatusRequest {
    /**
     * ID of employee
     */
    private Long id;
    /**
     * New flag status (true or false)
     */
    private Boolean fired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFired() {
        return fired;
    }

    public void setFired(Boolean fired) {
        this.fired = fired;
    }

}
