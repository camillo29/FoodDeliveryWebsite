package com.FoodDelivery.FoodDelivery.Security;

import com.FoodDelivery.FoodDelivery.Repository.RoleRepository;
import com.FoodDelivery.FoodDelivery.Repository.UserRepository;
import com.FoodDelivery.FoodDelivery.Model.User;
import com.FoodDelivery.FoodDelivery.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class validating tokens
 */
@Component
public class TokenValidator {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * Method checking if user bearing token have specified role
     * @param token - User token
     * @param role - Role that user needs to have
     * @return true if user has role, false if not
     */
    public boolean hasRole(String token, String role) {
        if (!jwtTokenUtil.isTokenExpired(token)) {
            User user = userRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
            for (Role r : user.getRoles()) {
                if (r.getName().equals(role))
                    return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Method checking if token has expired
     * @param token - user token
     * @return true if expired, false if not
     */
    public boolean hasExpired(String token){
        return jwtTokenUtil.isTokenExpired(token);
    }

}
