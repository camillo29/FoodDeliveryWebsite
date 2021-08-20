package com.FoodDelivery.FoodDelivery.Repository;

import com.FoodDelivery.FoodDelivery.Model.Office;
import com.FoodDelivery.FoodDelivery.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByOffice(Office office);
}
