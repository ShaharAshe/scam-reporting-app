package hac.ex5.service;

import hac.ex5.dto.RegistrationForm;
import hac.ex5.dto.TestRegisterForm;
import hac.ex5.model.User;
import hac.ex5.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for managing user registration and data.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new user with the details provided in the RegistrationForm.
     * @param form The registration form containing user details.
     *
     */
     public void registerNewUser(RegistrationForm form) {
        if (userRepository.findByUsername(form.getUserName()) == null) {
            User newUser = new User();
            newUser.setUsername(form.getUserName());
            newUser.setFirstName(form.getFirstName());
            newUser.setLastName(form.getLastName());
            newUser.setEmail(form.getEmail());
            newUser.setPassword(passwordEncoder.encode(form.getPassword())); // Encrypt the password before saving
            newUser.setRole("USER");

            newUser.setDateOfBirth(form.getDateOfBirth());
            newUser.setGender(form.getGender());
            newUser.setComments(form.getComments());

            userRepository.save(newUser);
        } else {
            throw new IllegalStateException("User already exists.");
        }
    }

    /**
     * Registers a new user based on a simplified form, primarily used for testing or simplified scenarios.
     * @param form The registration form with user details.
     */
    public void registerNewUser(TestRegisterForm form) {
        if (userRepository.findByUsername(form.getUserName()) == null) {
            User newUser = new User();
            newUser.setUsername(form.getUserName());
            newUser.setFirstName(form.getFirstName());
            newUser.setLastName(form.getLastName());
            newUser.setEmail(form.getEmail());
            newUser.setPassword(passwordEncoder.encode(form.getPassword())); // Encrypt the password before saving
            newUser.setRole("USER");

            newUser.setDateOfBirth(form.getDateOfBirth());
            newUser.setGender(form.getGender());
            newUser.setComments(form.getComments());

            userRepository.save(newUser);
        } else {
            throw new IllegalStateException("User already exists.");
        }
    }
}
