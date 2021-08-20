package com.FoodDelivery.FoodDelivery.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dishes")
@EntityListeners(AuditingEntityListener.class)
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Transient
    private Long amount;

    /*(@JsonIgnore
    @ManyToMany(mappedBy = "dishes")
    private Set<Order> orders;
    */

    @JsonIgnore
    @OneToMany(mappedBy = "dish")
    Set<OrderDish> orderDishes;

    public long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public void setName(String name){
        this.name = name;
    }

    public Dish(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    public Dish() {
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(Set<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
