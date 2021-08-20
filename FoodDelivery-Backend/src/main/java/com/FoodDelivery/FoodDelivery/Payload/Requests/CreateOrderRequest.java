package com.FoodDelivery.FoodDelivery.Payload.Requests;

import com.FoodDelivery.FoodDelivery.Model.Dish;
import com.FoodDelivery.FoodDelivery.Model.Office;

import java.util.List;

/**
 * Request class for creating new order
 */
public class CreateOrderRequest {
    /**
     * List of dishes
     */
    private List<Dish> dishes;
    /**
     * Office choosed by user
     */
    private Office office;
    /**
     * Address for delivery specified by user
     */
    private String address;

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

