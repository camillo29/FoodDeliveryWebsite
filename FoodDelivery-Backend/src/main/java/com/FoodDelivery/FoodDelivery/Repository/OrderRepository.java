package com.FoodDelivery.FoodDelivery.Repository;


import com.FoodDelivery.FoodDelivery.Model.Order;
import com.FoodDelivery.FoodDelivery.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByClient(User client);
    List<Order> findALlByEmployee(User employee);

}
