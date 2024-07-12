package fr.diginamic.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import fr.diginamic.hello.repositories.DepartmentRepository;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/departmentList")
    public ModelAndView getAllCities(Model model) {
        ModelAndView modelAndView = new ModelAndView("department/departmentList");
        model.addAttribute("departments", departmentRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/deleteDepartment/{code}")
    public String deleteCity(@PathVariable String code) {
        departmentRepository.deleteById(code);
        return "redirect:/departmentList";
    }
}
