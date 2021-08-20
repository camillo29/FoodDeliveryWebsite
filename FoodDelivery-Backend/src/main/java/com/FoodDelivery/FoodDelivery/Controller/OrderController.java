package com.FoodDelivery.FoodDelivery.Controller;

import com.FoodDelivery.FoodDelivery.Model.*;
import com.FoodDelivery.FoodDelivery.Payload.Responses.CustomResponse;
import com.FoodDelivery.FoodDelivery.Repository.*;
import com.FoodDelivery.FoodDelivery.Payload.Requests.CreateOrderRequest;
import com.FoodDelivery.FoodDelivery.Security.JwtTokenUtil;
import com.FoodDelivery.FoodDelivery.Security.TokenValidator;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Controller class for Order entities
 */
@RestController
@RequestMapping("/api/OrderController")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DishRepository dishRepository;
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    OrderDishRepository orderDishRepository;
    @Autowired
    TokenValidator validator;
    @Autowired
    JwtTokenUtil jwt;

    /**
     * GET endpoint for method that gets all orders
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message Unauthorized - User was not authorized to call this endpoint
     *          List<Order> - List containing fetched Orders from database
     */
    @GetMapping("/orders")
    public ResponseEntity<?> getOrders(@RequestHeader(value = "token") String token){
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else return ResponseEntity.ok().body(orderRepository.findAll());
    }

    /**
     * GET endpoint for method that fetched one order based on provided ID
     * @param id - ID for order to be fetched
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message Unauthorized - User was not authorized to call this endpoint
     *          Order order - order object that was found by provided ID
     * @throws Exception - if order was not found
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Order order = orderRepository.findById(id).orElseThrow(() -> new Exception("No such Order"));
            return ResponseEntity.ok().body(order);
        }
    }

    /**
     * GET endpoint for method that fetches orders based on user role
     * @param token - token for validating user calling this endpoint and for checking if this user is CLIENT or EMPLOYEE
     * @param criteria - criterium specifying if orders active or already delivered are to be fetched (true or false)
     * @return CustomResponse with message
     *              Unauthorized - User was not authorized to call this endpoint
     *              Error, user not found - If user based on token is null
     *              Error, no orders found - If no orders were found
     *         List<Order> filteredOrders - List containing orders after filtering based on criteria and user role
     */
    @GetMapping("/ordersByRole/{criteria}")
    public ResponseEntity<?> getClientOrders(@RequestHeader(value = "token") String token, @PathVariable(value = "criteria") Boolean criteria){
        if(validator.hasRole(token, "ADMIN") || validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else{
            User user = userRepository.findByUsername(jwt.getUsernameFromToken(token));
            if(user == null) return ResponseEntity.badRequest().body(new CustomResponse("message", "Error, user not found"));
            if(validator.hasRole(token, "CLIENT")){
                List<Order> orders = orderRepository.findAllByClient(user);
                List<Order> filteredOrders = new LinkedList<>();
                for(Order order: orders){
                    if(order.getDelivered() == criteria) filteredOrders.add(order);
                }
                return ResponseEntity.ok().body(filteredOrders);
            } else if(validator.hasRole(token, "EMPLOYEE")) {
                List<Order> orders = orderRepository.findALlByEmployee(user);
                List<Order> filteredOrders = new LinkedList<>();
                for(Order order: orders)
                    if(!order.getDelivered()) filteredOrders.add(order);
                return ResponseEntity.ok().body(filteredOrders);
            }
            return ResponseEntity.badRequest().body(new CustomResponse("message", "Error, no orders found"));
        }
    }

    /**
     * POST endpoint for method that creates new order. Employee with lowest count of active orders from choosed office is set to new order.
     * @param req - Body of request
     *          List<Dish> dishes - list of dishes to link to order
     *          Office office - office choosed by user
     *          String address - address provided by user
     * @param token - token for validating user
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          Order created - Order was successfully saved to database
     * @throws Exception - never thrown, can be deleted
     */
    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest req, @RequestHeader(value = "token") String token) throws Exception{
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            User client = userRepository.findByUsername(jwt.getUsernameFromToken(token));
            Order order = new Order();
            order.setOffice(req.getOffice());
            order.setClient(client);
            List<Person> employees = personRepository.findAllByOffice(order.getOffice());
            List<User> employeeUsers = new LinkedList<>();
            for (Person person: employees) {
                if(person.getFired() == true) employees.remove(person);
                    User tmpUser = userRepository.findByPerson(person);
                for(Role role: tmpUser.getRoles()){
                    if(role.getName().equals("EMPLOYEE")) employeeUsers.add(tmpUser);
                }
            }
            List<Order> orders = new LinkedList<>();
            User employee = employeeUsers.get(0); int orderCount = 99999;
            for (User userEmployee: employeeUsers){
                orders = orderRepository.findALlByEmployee(userEmployee);
                if(orders.size() < orderCount) {
                    employee = userEmployee;
                    orderCount = orders.size();
                }
            }
            order.setEmployee(employee);
            Set<Dish> dishes = new HashSet<Dish>();
            Double price = 0.0;
            for (Dish d : req.getDishes()) {
                price += d.getPrice() * d.getAmount();
                dishes.add(d);
            }
            price = Math.floor(100*price)/100;
            order.setTotalPrice(price);
            order.setAddress(req.getAddress());
            order.setDelivered(false);
            order = orderRepository.save(order);
            Set<OrderDish> orderDishes = new HashSet<>();
            for(Dish dish: dishes)
                orderDishes.add(orderDishRepository.save(new OrderDish(order, dish, dish.getAmount())));
            order.setOrderDishes(orderDishes);
            orderRepository.save(order);
            return ResponseEntity.ok().body(new CustomResponse("message", "Order created"));
        }

    }

    /**
     * DELETE endpoint for method that deletes order based on provided ID
     * @param id - ID of order to be deleted
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          Order deleted - order was found and deleted
     * @throws Exception - if order was not found
     */
    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Order order = orderRepository.findById(id).orElseThrow(() -> new Exception("No such Order"));
            List<OrderDish> links = orderDishRepository.findAllByOrder(order);
            for(OrderDish link: links){
                orderDishRepository.delete(link);
            }
            orderRepository.delete(order);
            return ResponseEntity.ok().body(new CustomResponse("message", "Order deleted"));
        }
    }

    /**
     * PUT endpoint for method that sets "delivered" flag on specific Order
     * @param id - ID of order to be updated
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *              Unauthorized - User was not authorized to call this endpoint
     *              Order delivered - Order was found based on ID and flag "delivered" was set to true
     * @throws Exception - if order was not found
     */
    @PutMapping("/deliverOrder/{id}")
    public ResponseEntity<?> deliverOrder(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(!validator.hasRole(token, "EMPLOYEE") || validator.hasExpired(token))
            return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Order order = orderRepository.findById(id).orElseThrow(()->new Exception("No such order ["+id+"]"));
            order.setDelivered(true);
            orderRepository.save(order);
            return ResponseEntity.ok().body(new CustomResponse("message", "Order delivered"));
        }
    }

}
