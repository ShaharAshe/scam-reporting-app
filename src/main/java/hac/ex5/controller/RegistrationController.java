package hac.ex5.controller;

import hac.ex5.dto.TestRegisterForm;
import hac.ex5.repo.UserRepository;
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
    private final UserRepository userRepository;
    private final UserService userService;
    private static final List<String> GENDERS = List.of("Male", "Female", "Other");

    // Constructor Injection
    public RegistrationController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    // YOU HAVE TWO REGISTRATION FORM OBJECTS:
    //1. RegistrationForm
    //2. TestRegisterForm - easier for testing simple validate (length = 1)
    @GetMapping("/signup")
    public String showForm(Model model) {
        model.addAttribute("registrationForm", new TestRegisterForm());
        model.addAttribute("genders", GENDERS);
        return "signup";
    }
    //AS WELL TO CHANGE THE OBJECT HERE
    @PostMapping("/signup")
    public String handleFormSubmission(@Valid @ModelAttribute("registrationForm") TestRegisterForm registrationForm, BindingResult bindingResult, Model model) {
        boolean hasErrors = userRepository.findByUsername(registrationForm.getUserName()) != null ||
                            userRepository.findByEmail(registrationForm.getEmail()) != null;

        if (bindingResult.hasErrors() || hasErrors) {
            System.out.println(registrationForm);
            System.out.println(bindingResult);
            model.addAttribute("genders", GENDERS);
            if (userRepository.findByUsername(registrationForm.getUserName()) != null)
                model.addAttribute("UserNameError", true);
            if (userRepository.findByEmail(registrationForm.getEmail()) != null)
                model.addAttribute("EmailError", true);
            return "signup";
        }

        userService.registerNewUser(registrationForm);
        return "redirect:/success";
    }
}
