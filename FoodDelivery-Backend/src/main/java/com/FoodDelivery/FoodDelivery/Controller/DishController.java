package com.FoodDelivery.FoodDelivery.Controller;

import com.FoodDelivery.FoodDelivery.Model.Dish;
import com.FoodDelivery.FoodDelivery.Payload.Responses.CustomResponse;
import com.FoodDelivery.FoodDelivery.Repository.DishRepository;
import com.FoodDelivery.FoodDelivery.Security.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for Dish entities
 */
@RestController
@RequestMapping("/api/DishController")
public class DishController {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    TokenValidator validator;

    /**
     * GET endpoint for method that gets all dishes from database
     * @return List<Dish> - list containing all dishes from database
     */
    @GetMapping("/dishes")
    public List<Dish> getAllDishes(){
            return dishRepository.findAll();
    }

    /**
     * GET endpoint for method that gets one dish based on provided ID
     * @param dishId - ID for dish to be fetched
     * @return ResponseEntity<Dish> - fetched dish
     * @throws Exception in case of not finding specific dish
     */
    @GetMapping("/dishes/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable(value = "id") Long dishId)
        throws Exception{
            Dish dish = dishRepository.findById(dishId)
                    .orElseThrow(() -> new Exception("Dish not found"));
            return (ResponseEntity.ok().body(dish));
    }

    /**
     * POST endpoint for method that creates new dish
     * @param dish - Dish object with all needed information to create new dish
     *             String name - name of the dish
     *             String description - description of the dish
     *             Long price - price of the dish
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          Dish added - Dish was successfully saved to database
     */
    @PostMapping("/createDish")
    public ResponseEntity<?> createDish(@RequestBody Dish dish, @RequestHeader(value = "token") String token){
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            dishRepository.save(dish);
            return ResponseEntity.ok().body(new CustomResponse("message", "Dish added"));
        }
    }

    /**
     * PUT endpoint for method that updates the dish
     * @param id - ID of the dish to be updated
     * @param token - token for validating user calling this endpoint
     * @param dishDetails - object with which dish will be updated
     * @return CustomResponse - with message Unauthorized if user was not authorized to call this endpoint
     *          Dish updatedDish - dish object after updating
     * @throws Exception - if dish was not found in database
     */
    @PutMapping("/updateDish/{id}")
    public ResponseEntity<?> updateDish(@PathVariable(value = "id") Long id,
                                           @RequestHeader(value = "token") String token,
                                           @RequestBody Dish dishDetails) throws Exception {
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Dish dish = dishRepository.findById(id).orElseThrow(() -> new Exception("Dish not found"));
            dish.setName(dishDetails.getName());
            dish.setDescription(dishDetails.getDescription());
            dish.setPrice(dishDetails.getPrice());
            final Dish updatedDish = dishRepository.save(dish);
            return ResponseEntity.ok(updatedDish);
        }
    }

    /**
     * DELETE endpoint for method that deletes dish based on provided ID
     * @param id - ID of dish to be deleted
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          Deleted - Dish was successfully deleted
     * @throws Exception - if dish was not found in database
     */
    @DeleteMapping("/deleteDish/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Dish dish = dishRepository.findById(id).orElseThrow(() -> new Exception("Dish not found"));
            dishRepository.delete(dish);
            return ResponseEntity.ok().body(new CustomResponse("message", "Deleted"));
        }
    }

}
