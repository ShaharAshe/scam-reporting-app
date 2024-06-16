package hac.ex5.controller;

import hac.ex5.dto.RegistrationForm;

import hac.ex5.dto.TestRegisterForm;
import hac.ex5.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String showForm(Model model) {
        model.addAttribute("registrationForm", new TestRegisterForm());
        model.addAttribute("genders", List.of("Male", "Female", "Other"));
        return "signup";
    }

    @PostMapping("/signup")
    public String handleFormSubmission(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", List.of("Male", "Female", "Other"));
            return "signup";
        }

        userService.registerNewUser(registrationForm);
        return "redirect:/success";
    }
}
