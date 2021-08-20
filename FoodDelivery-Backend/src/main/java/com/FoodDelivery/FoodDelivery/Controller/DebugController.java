package com.FoodDelivery.FoodDelivery.Controller;

import com.FoodDelivery.FoodDelivery.Model.*;
import com.FoodDelivery.FoodDelivery.Payload.Responses.CustomResponse;
import com.FoodDelivery.FoodDelivery.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Debug controller class used for filling database with initial
 * data when starting the application for the first time
 */
@RestController
@RequestMapping("/api/DebugController")
public class DebugController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    DishRepository dishRepository;
    @Autowired
    PasswordEncoder encoder;

    /**
     * Endpoint for method that fills database with some data to showcase the website
     * @return CustomResponse with message about success
     */
    @PostMapping("/initialFill")
    public ResponseEntity<?> initialFill(){
        //ROLES
        Role admin = roleRepository.save(new Role("ADMIN"));
        Role employee = roleRepository.save(new Role("EMPLOYEE"));
        Role client = roleRepository.save(new Role("CLIENT"));
        //------------
        //OFFICE
        Office officeWarsaw = officeRepository.save(new Office("Warszawa", "Warszawska 7", "01-475", "143674569"));
        Office officeCracow = officeRepository.save(new Office("Kraków", "Krakowska 10", "04-218", "745376954"));
        Office officeKielce = officeRepository.save(new Office("Kielce", "Kielecka 6", "25-001", "73546575"));
        //------------
        //PEOPLE
        Person adminPerson = personRepository.save(new Person("Jan", "Kowalski", "123456789", "jan_kowalski@op.pl", officeWarsaw));
        Person employeePersonWarsaw = personRepository.save(new Person("Krzysztof", "Janowski", "652368745", "krzysztof_janowski@op.pl", officeWarsaw));
        Person employeePersonCracow = personRepository.save(new Person("Kazimierz", "Król", "635463746", "kazimierz_krol@op.pl", officeCracow));
        Person employeePersonKielce = personRepository.save(new Person("Tadeusz", "Czarny", "735495772", "tadeusz_czarny@op.pl", officeKielce));
        Person clientPerson = personRepository.save(new Person("Piotr", "Drewno", "745385767", "piotr_drewno@op.pl", null));
        //------------
        //USERS
        User adminUser = userRepository.save(new User(adminPerson.getEMail(), encoder.encode("zaq1@WSX"), adminPerson, admin));
        User employeeUserWarsaw = userRepository.save(new User(employeePersonWarsaw.getEMail(), encoder.encode("zaq1@WSX"), employeePersonWarsaw , employee));
        User employeeUserCracow = userRepository.save(new User(employeePersonCracow.getEMail(), encoder.encode("zaq1@WSX"), employeePersonCracow, employee));
        User employeeUserKielce = userRepository.save(new User(employeePersonKielce.getEMail(), encoder.encode("zaq1@WSX"), employeePersonKielce, employee));
        User clientUser = userRepository.save(new User(clientPerson.getEMail(), encoder.encode("zaq1@WSX"), clientPerson, client));
        //------------
        //DISHES
        dishRepository.save(new Dish("Spaghetti Bolognese", "Super włoskie 10/10", 19.99));
        dishRepository.save(new Dish("Ryż Chiński", "Ryż z Chin", 19.99));
        dishRepository.save(new Dish("Sałatka z selerem konserwowym", "Szynka konserwowa, Ogórek kiszony, Żółty ser. Koniecznie wypróbujcie tej pysznej sałatki!", 14.99));
        dishRepository.save(new Dish("Sałatka gyros", "Sałatka gyros to pyszna, wielowarstwowa przekąska imprezowa, idealna na każde możliwe przyjęcie. Niezależnie czy to urodziny, imieniny, czy zwykła domówka… Zawsze wygląda świetnie na stole!", 12.99));
        dishRepository.save(new Dish("Sznycelki drobiowe po francusku", "Ekstra francuskie!", 20.0));
        //------------
        return ResponseEntity.ok().body(new CustomResponse("message", "Database filled!"));
    }

    /**
     * Endpoint for method that checks if there is data in database (checking if roles exists as it is the most important thing in database and application)
     * @return CustomResponse with message that can be "true" - data exists, or "false" - data don't exists
     */
    @GetMapping("/checkIfDataExists")
    public ResponseEntity<?> checkIfDataExists(){
        List<Role> roles = roleRepository.findAll();
        if(roles.size()==0) return ResponseEntity.badRequest().body(new CustomResponse("message", "false"));
        else return ResponseEntity.ok().body(new CustomResponse("message", "true"));
    }
}
