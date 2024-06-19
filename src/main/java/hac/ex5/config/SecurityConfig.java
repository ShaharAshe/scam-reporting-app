package hac.ex5.config;

import hac.ex5.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;


@Configuration
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public void userDetailsService() {
        if (userRepository.findByUsername("admin") == null) {
            hac.ex5.model.User newUser = new hac.ex5.model.User();
            newUser.setUsername("admin");
            System.out.println("newUser = " + newUser.getUsername());
            newUser.setFirstName("admin");
            System.out.println("New user created with first name: " + newUser.getFirstName());
            newUser.setLastName("admin");
            System.out.println("New user created with last name: " + newUser.getLastName());
            newUser.setEmail("admin@admin.com");
            System.out.println("New user created with email: " + newUser.getEmail());
            newUser.setPassword(passwordEncoder.encode("admin"));  // Encrypt the password before saving
            System.out.println("New user created with password: " + newUser.getPassword());
            newUser.setRole("ADMIN");


            userRepository.save(newUser);
            //ID IS ONLY CREATED AFTER WE .save
            System.out.println("New user registered with id: {}" + newUser.getId());
        } else {
            System.out.println("Admin user already exists, skipping creation.");
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/css/**","/media/**", "/", "/403", "/errorpage", "/simulateError", "/signup", "/success","/feed", "/").permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/403")
                );

        return http.build();
    }
}
