package com.stickerdon.admin.controller;

import com.stickerdon.library.model.Category;
import com.stickerdon.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Category");
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @PostMapping("/add-category")
    public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes redirectAttributes) {
        try {
            categoryService.save(category);
            redirectAttributes.addFlashAttribute("success", "Category added");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Category already exist");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to create category");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(@PathVariable Long id){
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes){
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success","Updated successfully");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "Category deleted");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete category");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable Long id, RedirectAttributes attributes){
        try {
            categoryService.enabledById(id);
            attributes.addFlashAttribute("success", "Category activated");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable category");
        }
        return "redirect:/categories";
    }
}
