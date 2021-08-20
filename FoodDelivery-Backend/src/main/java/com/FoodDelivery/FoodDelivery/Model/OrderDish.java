package com.FoodDelivery.FoodDelivery.Model;

import javax.persistence.*;

@Entity
public class OrderDish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    Dish dish;

    Long amount;

    public OrderDish() {}

    public OrderDish(Order order, Dish dish, Long amount) {
        this.order = order;
        this.dish = dish;
        this.amount = amount;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
