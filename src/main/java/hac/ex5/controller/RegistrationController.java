package hac.ex5.controller;

import hac.ex5.dto.TestRegisterForm;
import hac.ex5.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RegistrationController {

    private final UserService userService;

    // Constructor Injection
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
    // YOU HAVE TWO REGISTRATION FORM OBJECTS:
    //1. RegistrationForm
    //2. TestRegisterForm - eaiser for testing simple validate (length = 1)
    @GetMapping("/signup")
    public String showForm(Model model) {
        model.addAttribute("registrationForm", new TestRegisterForm());
        model.addAttribute("genders", List.of("Male", "Female", "Other"));
        return "signup";
    }
    //AS WELL TO CHANGE THE OBJECT HERE
    @PostMapping("/signup")
    public String handleFormSubmission(@Valid @ModelAttribute("registrationForm") TestRegisterForm registrationForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(registrationForm);
            System.out.println(bindingResult);
            model.addAttribute("genders", List.of("Male", "Female", "Other"));
            return "signup";
        }

        userService.registerNewUser(registrationForm);
        return "redirect:/success";
    }
}
