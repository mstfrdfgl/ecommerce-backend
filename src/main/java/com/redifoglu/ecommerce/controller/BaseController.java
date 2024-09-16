package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.exceptions.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class BaseController {

    protected Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new UnauthorizedException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return parseUserId(userDetails);
    }

    private Long parseUserId(UserDetails userDetails) {
        if (userDetails instanceof Admin) {
            return ((Admin) userDetails).getId();
        } else if (userDetails instanceof Customer) {
            return ((Customer) userDetails).getId();
        } else if (userDetails instanceof Seller) {
            return ((Seller) userDetails).getId();
        }
        throw new UnauthorizedException("Unsupported user type");
    }
}
