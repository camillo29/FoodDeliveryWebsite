package com.FoodDelivery.FoodDelivery.Repository;

import com.FoodDelivery.FoodDelivery.Model.Order;
import com.FoodDelivery.FoodDelivery.Model.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
    List<OrderDish> findAllByOrder(Order order);
}
