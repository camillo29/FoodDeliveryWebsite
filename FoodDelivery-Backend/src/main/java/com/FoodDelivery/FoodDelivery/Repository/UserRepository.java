package com.FoodDelivery.FoodDelivery.Repository;

import com.FoodDelivery.FoodDelivery.Model.Person;
import com.FoodDelivery.FoodDelivery.Model.Role;
import com.FoodDelivery.FoodDelivery.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);
    User findByPerson(Person person);
    List<User> findAllByRoles(Role role);
    //UserDetails findByUsername(String username);

}
