package hac.ex5.service;


import hac.ex5.dto.RegistrationForm;
import hac.ex5.model.User;
import hac.ex5.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(RegistrationForm form) {
        User newUser = new User();
        newUser.setFirstName(form.getFirstName());
        newUser.setLastName(form.getLastName());
        newUser.setEmail(form.getEmail());
        newUser.setPassword(passwordEncoder.encode(form.getPassword()));  // Encrypt the password before saving

        // Additional logic can be added here, such as setting roles, sending confirmation emails, etc.

        return userRepository.save(newUser);  // Save the new user to the database
    }
}
