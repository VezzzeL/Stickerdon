package com.stickerdon.admin.controller;

import com.stickerdon.library.model.Admin;
import com.stickerdon.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/account")
    public String accountHome(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Admin admin = adminService.findByUsername(username);
        model.addAttribute("admin", admin);

        return "account";
    }

    @RequestMapping(value = "update-admin", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updateAdmin(@ModelAttribute("admin") Admin admin, Model model, RedirectAttributes attributes,
                              Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Admin adminSaved = adminService.saveInfo(admin);
        attributes.addFlashAttribute("admin", adminSaved);
        return "redirect:/account";
    }
}
