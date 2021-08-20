package com.FoodDelivery.FoodDelivery.Controller;

import com.FoodDelivery.FoodDelivery.Model.Office;
import com.FoodDelivery.FoodDelivery.Model.Person;
import com.FoodDelivery.FoodDelivery.Model.Role;
import com.FoodDelivery.FoodDelivery.Model.User;
import com.FoodDelivery.FoodDelivery.Payload.Requests.*;
import com.FoodDelivery.FoodDelivery.Payload.Responses.CustomResponse;
import com.FoodDelivery.FoodDelivery.Payload.Responses.EmployeeResponse;
import com.FoodDelivery.FoodDelivery.Payload.Responses.SelfInformationResponse;
import com.FoodDelivery.FoodDelivery.Repository.OfficeRepository;
import com.FoodDelivery.FoodDelivery.Repository.PersonRepository;
import com.FoodDelivery.FoodDelivery.Repository.RoleRepository;
import com.FoodDelivery.FoodDelivery.Repository.UserRepository;
import com.FoodDelivery.FoodDelivery.Payload.Responses.LoginResponse;
import com.FoodDelivery.FoodDelivery.Security.JwtTokenUtil;
import com.FoodDelivery.FoodDelivery.Security.TokenValidator;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Controller class for User entities
 */
@RestController
@RequestMapping("/api/UserController")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    TokenValidator validator;


    /**
     * GET endpoint for method that gets all users from database
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message Unauthorized - User was not authorized to call this endpoint
     *         List<User> - list containing all users from database
     */
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestHeader(value = "token") String token){
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else return ResponseEntity.ok().body(userRepository.findAll());
    }

    /**
     * GET endpoint for method that checks if provided user have provided role
     * @param role - role for checking
     * @param token - token for getting and checking if user have that role
     * @return CustomResponse with message
     *          Unauthorized - Token has expired or user don't have specified role
     *          Authorized - User have specified role
     */
    @GetMapping("/verifyRole/{role}")
    public ResponseEntity<?> verifyRole(@PathVariable(value = "role") String role, @RequestHeader(value = "token") String token) {
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            if(validator.hasRole(token, role)) return ResponseEntity.ok().body(new CustomResponse("message", "Authorized"));
            else return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        }
    }

    /**
     * GET endpoint for method that gets one user based on provided email
     * @param email - email of user to be fetched
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *              Unauthorized - User token has expired
     *              No such user - user was not found
     *         User user - found user
     */
    @GetMapping("/users/{email}")
    public ResponseEntity<?> getUserByUserName(@PathVariable(value = "email") String email, @RequestHeader(value = "token") String token) {
        if (validator.hasExpired(token))
            return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            User user = userRepository.findByUsername(email);
            if (user != null) return ResponseEntity.ok().body(user);
            else return ResponseEntity.badRequest().body(new CustomResponse("message", "No such user"));
        }
    }

    /**
     * GET endpoint for method that gets all employees from database
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *              Unauthorized - User was not authorized to call this endpoint
     *         List<EmployeeResponse> - list containing all users that are employees
     */
    @GetMapping("/employees")
    public ResponseEntity<?> getEmployees(@RequestHeader(value = "token") String token){
        if(!validator.hasExpired(token) && validator.hasRole(token, "ADMIN")){
            Role role = roleRepository.findOneByName("EMPLOYEE");
            List<User> employees = userRepository.findAllByRoles(role);
            List<EmployeeResponse> convertedEmployees = new LinkedList<>();
            for(User user: employees){
                convertedEmployees.add(new EmployeeResponse(user));
            }
            return ResponseEntity.ok().body(convertedEmployees);
        } else return ResponseEntity.badRequest().body(new CustomResponse("message", "Not authorized"));
    }

    /**
     * GET endpoint for method that gets information about user who is calling it
     * @param token - token for validating user calling this endpoint and providing user email
     * @return CustomResponse with message
     *          Unauthorized - User token has expired
     *          Error, user not found - user was not found in database
     *          SelfInformationResponse response - Object containing information about user (String name, surname, phoneNumber, email; Office office;)
     */
    @GetMapping("/selfInformation")
    public ResponseEntity<?> getSelfInformation(@RequestHeader(value = "token") String token){
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            User user = userRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
            if(user == null) return ResponseEntity.badRequest().body(new CustomResponse("message", "Error, user not found"));
            SelfInformationResponse response = new SelfInformationResponse(user.getPerson().getName(), user.getPerson().getSurname(), user.getPerson().getPhoneNumber(), user.getUsername(), user.getPerson().getOffice());
            return ResponseEntity.ok().body(response);
        }
    }

    /**
     * POST endpoint for method that signups user
     * @param req - Request body
     *     String name - name of user
     *     String surname - surname of user
     *     String phoneNumber - user phone number
     *     String email - user email, used for logging in
     *     String password - user password, gets encoded with PasswordEncoder
     *     Long officeId - Id of office in which user will be working, null for clients
     *     String roleName - name of role user will be having
     * @return CustomResponse with message
     *          No such role - Role was not found
     *          Email already in use - provided email is taken
     *          Signed up successfully - user was saved to database
     * @throws Exception - if office was not found
     */
    @PostMapping("/signUp")
    public ResponseEntity<?> createUser(@RequestBody SignUpRequest req) throws Exception{
        Office office = null;
        Role role = roleRepository.findOneByName(req.getRoleName());
        if(role==null)
            return ResponseEntity.badRequest().body(new CustomResponse("message", "No such role"));
        if(userRepository.findByUsername(req.getEmail()) != null)
            return ResponseEntity.badRequest().body(new CustomResponse("message", "Email already in use"));
        if(req.getOfficeId() != null)
            office = officeRepository.findById(req.getOfficeId()).orElseThrow(()->new Exception("No such office"));
        final Person person = personRepository.save(new Person(req.getName(), req.getSurname(), req.getPhoneNumber(), req.getEmail(), office));
        final User user = new User(req.getEmail(), encoder.encode(req.getPassword()), person, role);
        userRepository.save(user);
        return ResponseEntity.ok().body(new CustomResponse("message", "Signed up successfully"));
    }

    /**
     * POST endpoint for method allowing to sign in into website
     * @param req Request body
     *            String email - user email
     *            String password - user password, gets compared with encoded password by PasswordEncoder
     * @return CustomResponse with message
     *              Wrong email - email was not found in database
     *              You are fired - if fired employee tries to sign in with his work account
     *              Wrong password - password doesn't match with the one saved in database
     *         LoginResponse - custom response containing JWT token and email
     */
    @PostMapping("/signIn")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest req) {
        User user = userRepository.findByUsername(req.getEmail());
        if(user == null) {
            return ResponseEntity.badRequest().body(new CustomResponse("message", "Wrong email"));
        }
        if(user.getPerson().getFired() == true) return ResponseEntity.badRequest().body(new CustomResponse("message","You are fired!"));
        if(encoder.matches(req.getPassword(), user.getPassword()))
            return ResponseEntity.ok().body(new LoginResponse(jwtTokenUtil.generateToken(user), req.getEmail()));
        else return ResponseEntity.badRequest().body(new CustomResponse("message", "Wrong password"));
    }

    /**
     * PUT endpoint for method that updates the user
     * @param token - token for validating user calling this endpoint
     * @param req - Request body
     *          String email - email
     *          String password - password
     *          Long personId - person ID
     *          Long roleId - role ID
     * @return CustomResponse with message
     *              Unauthorized - User token has expired
     * @throws Exception - if role was not found or person was not found
     */
    @PutMapping("/updateUser")
    public ResponseEntity<?>updateUser(@RequestHeader(value = "token") String token,
                                          @RequestBody CreateUserRequest req) throws Exception{
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            User oldUser = userRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
            Role role = roleRepository.findById(req.getRoleId()).orElseThrow(() -> new Exception("No such role"));
            Person person = personRepository.findById(req.getPersonId()).orElseThrow(() -> new Exception("No such person"));
            oldUser.setUsername(req.getEmail());
            oldUser.setPassword(req.getPassword());
            User updatedUser = userRepository.save(oldUser);
            return ResponseEntity.ok().body(updatedUser);
        }
    }

    /**
     * PUT endpoint for method that allows changing password
     * @param token - token for validating user calling this endpoint
     * @param req - Request body
     *               String oldPassword - old password for comparing
     *               String newPassword - new password for updating
     * @return CustomResponse with message
     *          Unauthorized - User token has expired
     *          Error, user not found - user was not found
     *          Password changed - password was successfully changed
     *          Passwords doesn't match! - oldPassword doesnt match password saved in database
     */
    @PutMapping("/changePassword")
    public ResponseEntity<?>changePassword(@RequestHeader(value = "token") String token, @RequestBody ChangePasswordRequest req){
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            User user = userRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
            if(user == null) return ResponseEntity.badRequest().body(new CustomResponse("message", "Error, user not found"));
            if(encoder.matches(req.getOldPassword(), user.getPassword())){
                user.setPassword(encoder.encode(req.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.ok().body(new CustomResponse("message", "Password changed"));
            } else return ResponseEntity.badRequest().body(new CustomResponse("message", "Passwords doesn't match!"));
        }
    }

    /**
     * PUT endpoint for method that changes employee status flag
     * @param token - token for validating user calling this endpoint
     * @param req - Request body
     *              Long id - id of employee
     *              Boolean fired - flag to be set on fired field (true or false)
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          Employee status changed - flag was successfully set
     * @throws Exception - if user was not found
     */
    @PutMapping("/changeEmployeeStatus")
    public ResponseEntity<?> changeEmployeeStatus(@RequestHeader(value = "token") String token, @RequestBody ChangeEmployeeStatusRequest req) throws Exception{
        if(!validator.hasExpired(token) && validator.hasRole(token, "ADMIN")){
            User user = userRepository.findById(req.getId()).orElseThrow(()->new Exception("User not found"));
            Person person = user.getPerson();
            person.setFired(req.getFired());
            personRepository.save(person);
            return ResponseEntity.ok().body(new CustomResponse("message", "Employee status changed"));
        } else {
            return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        }
    }

    /**
     * DELETE endpoint for method that deleted user
     * @param id - ID of user to be deleted
     * @param token - token for validating user calling this endpoint
     * @return CustomResponse with message
     *          Unauthorized - User was not authorized to call this endpoint
     *          User deleted - user was found and cascade deleted
     * @throws Exception - if user was not found
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<CustomResponse> deleteUser(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(!validator.hasRole(token, "ADMIN")) return ResponseEntity.badRequest().body(new CustomResponse("message", "Unauthorized"));
        else {
            User user = userRepository.findById(id).orElseThrow(() -> new Exception("No such user"));
            Person person = user.getPerson();
            userRepository.delete(user);
            personRepository.delete(person);
            return ResponseEntity.ok().body(new CustomResponse("message", "User deleted"));
        }
    }
}
