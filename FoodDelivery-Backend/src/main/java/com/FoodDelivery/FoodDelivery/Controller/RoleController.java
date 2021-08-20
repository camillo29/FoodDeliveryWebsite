package com.FoodDelivery.FoodDelivery.Controller;

import com.FoodDelivery.FoodDelivery.Model.Role;
import com.FoodDelivery.FoodDelivery.Payload.Responses.CustomResponse;
import com.FoodDelivery.FoodDelivery.Repository.RoleRepository;
import com.FoodDelivery.FoodDelivery.Security.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for Role entities
 */
@RestController
@RequestMapping("/api/RoleController")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TokenValidator validator;

    /**
     * GET endpoint for method that gets all roles
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message Unauthorized - User was not authorized to call this endpoint
     *          List<Role> - list containing all roles fetched from database
     */
    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(@RequestHeader(value = "token") String token){
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else return ResponseEntity.ok().body(roleRepository.findAll());
    }

    /**
     * GET endpoint for method that gets one role based on provided ID
     * @param id - ID of role to be found
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message Unauthorized - User was not authorized to call this endpoint
     *          Role role - found role
     * @throws Exception - if role was not found
     */
    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRole(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Role role = roleRepository.findById(id).orElseThrow(() -> new Exception("No such role"));
            return ResponseEntity.ok().body(role);
        }
    }

    /**
     * POST endpoint for method that creates new role
     * @param role - Role object to be saved
     *             String name - name of role
     * @param token - token for validating user calling this endpoint
     * @return Role newRole - new role saved to database
     */
    @PostMapping("/createRole")
    public ResponseEntity<?> createRole(@RequestBody Role role, @RequestHeader(value = "token") String token){
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        Role newRole = roleRepository.save(role);
        return ResponseEntity.ok().body(newRole);
    }

    /**
     * DELETE endpoint for method that deletes role based on ID
     * @param id - ID of role to be deleted
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          Role deleted - Role was found and successfully deleted
     * @throws Exception - if role was not found
     */
    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<CustomResponse> deleteRole(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        Role role = roleRepository.findById(id).orElseThrow(()->new Exception("No such Role"));
        roleRepository.delete(role);
        return ResponseEntity.ok().body(new CustomResponse("message", "Role deleted"));
    }
}
