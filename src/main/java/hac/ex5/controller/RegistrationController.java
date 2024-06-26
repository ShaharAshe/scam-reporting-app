package hac.ex5.controller;

import hac.ex5.dto.RegistrationForm;
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



/**
 * Controller for handling user registration.
 */

@Controller
public class RegistrationController {
    private final UserRepository userRepository;
    private final UserService userService;
    private static final List<String> GENDERS = List.of("Male", "Female", "Other");
        /**
     * Constructor for dependency injection.
     * 
     * @param userService The service dealing with user operations.
     * @param userRepository The repository for user data access.
     */
    // Constructor Injection
    public RegistrationController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
       /**
     * Displays the registration form.
     * 
     * @param model The model for the view to add attributes to.
     * @return The name of the registration form view template.
     */
    @GetMapping("/signup")
    public String showForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        model.addAttribute("genders", GENDERS);
        return "signup";
    }
        /**
     * Handles the submission of the registration form.
     * 
     * @param registrationForm The form object bound to the input data.
     * @param bindingResult Contains validation results.
     * @param model The model to add attributes to for the view.
     * @return Redirection to the success page or back to the form if there are errors.
     */

        @PostMapping("/signup")
        public String handleFormSubmission(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm, BindingResult bindingResult, Model model) {
            // Check for field-specific errors detected by @Valid
            if (bindingResult.hasErrors()) {
                model.addAttribute("genders", GENDERS);
                return "signup";
            }

            // Check for business logic errors such as duplicate usernames or emails
            boolean usernameExists = userRepository.findByUsername(registrationForm.getUserName()) != null;
            boolean emailExists = userRepository.findByEmail(registrationForm.getEmail()) != null;

            if (usernameExists || emailExists) {
                if (usernameExists) {
                    bindingResult.rejectValue("userName", "error.userName", "An account with this username already exists.");
                }
                if (emailExists) {
                    bindingResult.rejectValue("email", "error.email", "An account with this email already exists.");
                }
                model.addAttribute("genders", GENDERS);
                return "signup";
            }

            // Proceed to register the user if no errors
            userService.registerNewUser(registrationForm);
            return "redirect:/success";
        }

}
