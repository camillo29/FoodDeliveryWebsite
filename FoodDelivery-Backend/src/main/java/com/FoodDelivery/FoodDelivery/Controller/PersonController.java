package com.FoodDelivery.FoodDelivery.Controller;

import com.FoodDelivery.FoodDelivery.Model.Office;
import com.FoodDelivery.FoodDelivery.Model.Person;
import com.FoodDelivery.FoodDelivery.Payload.Responses.CustomResponse;
import com.FoodDelivery.FoodDelivery.Repository.OfficeRepository;
import com.FoodDelivery.FoodDelivery.Repository.PersonRepository;
import com.FoodDelivery.FoodDelivery.Payload.Requests.CreatePersonRequest;
import com.FoodDelivery.FoodDelivery.Repository.UserRepository;
import com.FoodDelivery.FoodDelivery.Security.TokenValidator;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for Person entities
 */
@RestController
@RequestMapping("/api/PersonController")
public class PersonController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    TokenValidator validator;

    /**
     * GET endpoint for method that gets all People from database
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message Unauthorized - User was not authorized to call this endpoint
     *          List<Person> - containing all person objects fetched from database
     */
    @GetMapping("/people")
    public ResponseEntity<?> getPeople(@RequestHeader(value = "token") String token){
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else return ResponseEntity.ok().body(personRepository.findAll());
    }

    /**
     * GET endpoint for method that gets one person based on provided ID
     * @param id - ID of person to be fetched
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message Unauthorized - User was not authorized to call this endpoint
     *          Person person - fetched person
     * @throws Exception - if person was not found
     */
    @GetMapping("/people/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Person person = personRepository.findById(id).orElseThrow(() -> new Exception("No such Person"));
            return ResponseEntity.ok().body(person);
        }
    }

    /**
     * POST endpoint for method that creates new person
     * @param req - Request body
     *            String name - name
     *            String surname - surname
     *            String phoneNumber - phone number
     *            String email - email, used for logging in
     *            Long officeId - office ID in which person works, is null for clients
     * @return Person person - newly created person
     * @throws Exception - if office was not found or no email was provided
     */
    @PostMapping("/createPerson")
    public Person createPerson(@RequestBody CreatePersonRequest req) throws Exception{
        Person person = new Person();
        Office office = officeRepository.findById(req.getOfficeId()).orElseThrow(()-> new Exception("No such Office"));
        if(req.getEmail() == null) throw new Exception("NO EMAIL PROVIDED");
        person.setName(req.getName());
        person.setSurname(req.getSurname());
        person.setEMail(req.getEmail());
        person.setPhoneNumber(req.getPhoneNumber());
        person.setOffice(office);

        return personRepository.save(person);
    }

    /**
     * PUT endpoint for method that updates person
     * @param id - ID of person to be updated
     * @param token - token for validating user calling this endpoint
     * @param req - Request body containing information for updating
     * @return CustomResponse with message Unauthorized - User was not authorized to call this endpoint
     *          Person updatedPerson - updated person
     * @throws Exception - if person was not found or if office was not found
     */
    @PutMapping("/updatePerson/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable(value = "id") Long id,
                                               @RequestHeader(value = "token") String token,
                                               @RequestBody CreatePersonRequest req) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Person oldPerson = personRepository.findById(id).orElseThrow(() -> new Exception("No such Person"));
            oldPerson.setName(req.getName());
            oldPerson.setSurname(req.getSurname());
            oldPerson.setPhoneNumber(req.getPhoneNumber());
            oldPerson.setEMail(req.getEmail());

            Office office = officeRepository.findById(req.getOfficeId()).orElseThrow(() -> new Exception("No such Office"));
            oldPerson.setOffice(office);
            final Person updatedPerson = personRepository.save(oldPerson);
            return ResponseEntity.ok().body(updatedPerson);
        }
    }

    /**
     * DELETE endpoint for method that deletes person based on provided ID
     * @param id - ID of person to be deleted
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          Person deleted - person was found and successfully deleted
     * @throws Exception  - if person was not found
     */
    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity<CustomResponse> deletePerson(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            Person person = personRepository.findById(id).orElseThrow(() -> new Exception("No such Person"));
            personRepository.delete(person);
            return ResponseEntity.ok().body(new CustomResponse("message", "Person deleted"));
        }
    }


}
