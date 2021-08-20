package com.FoodDelivery.FoodDelivery.Controller;

import com.FoodDelivery.FoodDelivery.Model.Office;
import com.FoodDelivery.FoodDelivery.Model.User;
import com.FoodDelivery.FoodDelivery.Payload.Responses.CustomResponse;
import com.FoodDelivery.FoodDelivery.Repository.OfficeRepository;
import com.FoodDelivery.FoodDelivery.Repository.UserRepository;
import com.FoodDelivery.FoodDelivery.Security.JwtTokenUtil;
import com.FoodDelivery.FoodDelivery.Security.TokenValidator;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for Office entities
 */
@RestController
@RequestMapping("/api/OfficeController")
public class OfficeController {
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    TokenValidator validator;

    /**
     * GET endpoint for method that gets all Offices from database
     * @return List<Office> - list containing all offices
     */
    @GetMapping("/offices")
    public List<Office> getAllOffices(){
        return officeRepository.findAll();
    }

    /**
     * GET endpoint for method that gets one office based on provided ID
     * @param id - ID of office to be fetched
     * @return Office - found office based on ID
     * @throws Exception - if office was not found
     */
    @GetMapping("/offices/{id}")
    public ResponseEntity<?> getOfficeById(@PathVariable(value = "id") Long id) throws Exception{
        Office office = officeRepository.findById(id).orElseThrow(()->new Exception("No such office"));
        return ResponseEntity.ok().body(office);
    }

    /**
     * POST endpoint for method that creates new office
     * @param office - Office object with all needed information
     *               String city - city
     *               String street - street
     *               String postCode - post code
     *               String phoneNumber - phoneNumber
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          Office added - office was successfully saved in database
     */
    @PostMapping("/createOffice")
    public ResponseEntity<?> createOffice(@RequestBody Office office, @RequestHeader(value = "token") String token) {
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            officeRepository.save(office);
            return ResponseEntity.ok().body(new CustomResponse("message", "Office added"));
        }
    }

    /**
     * PUT endpoint for method that updates the office
     * @param id - ID of office to be updated
     * @param token - token for validating user calling this endpoint
     * @param newOffice - Office object containing information for updating
     * @return CustomResponse with message Unauthorized - User was not authorized to call this endpoint
     *          Office updatedOffice - updated office
     * @throws Exception - if office was not found
     */
    @PutMapping("/updateOffice/{id}")
    public ResponseEntity<?> updateOffice(@PathVariable(value = "id") Long id,
                                               @RequestHeader(value = "token") String token,
                                               @RequestBody Office newOffice) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Office office = officeRepository.findById(id).orElseThrow(() -> new Exception("No such office"));
            office.setCity(newOffice.getCity());
            office.setStreet(newOffice.getStreet());
            office.setPostCode(newOffice.getPostCode());
            office.setPhoneNumber(newOffice.getPhoneNumber());
            final Office updatedOffice = officeRepository.save(office);
            return ResponseEntity.ok().body(updatedOffice);
        }
    }

    /**
     * DELETE endpoint for method that deletes office
     * @param id - ID of office to be deleted
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          Office deleted - Office was found and successfully deleted
     * @throws Exception - if office was not found
     */
    @DeleteMapping("/deleteOffice/{id}")
    public ResponseEntity<?> deleteOffice(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Office office = officeRepository.findById(id).orElseThrow(() -> new Exception("No such Office"));
            officeRepository.delete(office);
            return ResponseEntity.ok().body(new CustomResponse("message", "Office deleted"));
        }
    }
}
