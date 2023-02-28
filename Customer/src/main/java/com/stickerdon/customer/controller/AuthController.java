package com.stickerdon.customer.controller;

import com.stickerdon.library.dto.CustomerDto;
import com.stickerdon.library.model.Customer;
import com.stickerdon.library.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private CustomerService customerService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(CustomerService customerService, BCryptPasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }


    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }


    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                  BindingResult result,
                                  Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            Customer customer = customerService.findByUsername(customerDto.getUsername());
            if(customer != null){
                model.addAttribute("username", "Username have been registered");
                model.addAttribute("customerDto",customerDto);
                return "register";
            }
            if(customerDto.getPassword().equals(customerDto.getRepeatPassword())){
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("success", "Register successfully");
                return "register";
            }else{
                model.addAttribute("password", "Password mismatch");
                model.addAttribute("customerDto",customerDto);
                return "register";
            }
        }catch (Exception e){
            model.addAttribute("error", "Server error");
            model.addAttribute("customerDto",customerDto);
        }
        return "register";
    }
}

