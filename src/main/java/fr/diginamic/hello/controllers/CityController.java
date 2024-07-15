package fr.diginamic.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import fr.diginamic.hello.repositories.CityRepository;

@Controller
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/cityList")
    public ModelAndView getAllCities(Model model) {
        ModelAndView modelAndView = new ModelAndView("city/cityList");
        model.addAttribute("cities", cityRepository.findAll());
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteCity/{id}")
    public String deleteCity(@PathVariable int id) {
        cityRepository.deleteById(id);
        return "redirect:/cityList";
    }

}
