package com.tools.controller.main;

import com.tools.model.User;
import com.tools.repo.OrderingRepo;
import com.tools.repo.StatToolRepo;
import com.tools.repo.ToolRepo;
import com.tools.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Main {

    @Autowired
    protected UserRepo userRepo;
    @Autowired
    protected OrderingRepo orderingRepo;
    @Autowired
    protected StatToolRepo statToolRepo;
    @Autowired
    protected ToolRepo toolRepo;

    @Value("${upload.img}")
    protected String uploadImg;

    protected User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return userRepo.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getUserRole() {
        User user = getUser();
        if (user == null) return "NOT";
        return user.getRole().name();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}