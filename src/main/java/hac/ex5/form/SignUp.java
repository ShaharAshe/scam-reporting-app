package hac.ex5.form;

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
public class SignUp {
    @Autowired
    private FormDataRepository formDataRepository;

    @GetMapping("/signup")
    public String showForm(Model model) {
        model.addAttribute("formData", new FormData());
        model.addAttribute("genders", List.of("Male", "Female", "Other"));
        return "signup";
    }

    @PostMapping("/signup")
    public String handleFormSubmission(@Valid @ModelAttribute("formData") FormData formData, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", List.of("Male", "Female", "Other"));
            return "signup";
        }

        // Save form data to the database
        formDataRepository.save(formData);

        // Process the form data (e.g., save to database)
        return "Success"; // Redirect to a success page or return a success view
    }
}
