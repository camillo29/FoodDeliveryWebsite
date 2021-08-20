package com.FoodDelivery.FoodDelivery.Repository;

import com.FoodDelivery.FoodDelivery.Model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
}
