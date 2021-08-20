package com.FoodDelivery.FoodDelivery.Repository;

import com.FoodDelivery.FoodDelivery.Model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
}
