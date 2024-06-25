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
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * Security configuration class for Spring Security setup.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Configures a default user details service that checks for the existence of an admin user and creates one if not present.
     * This method is run at the start-up of the application to ensure the presence of an administrative user.
     */
    @Bean
    public void userDetailsService() {
        if (userRepository.findByUsername("admin") == null) {
            hac.ex5.model.User newUser = new hac.ex5.model.User();
            newUser.setUsername("admin");
            newUser.setFirstName("admin");
            newUser.setLastName("admin");
            newUser.setEmail("admin@admin.com");
            newUser.setPassword(passwordEncoder.encode("admin")); // Encrypt the password
            newUser.setRole("ADMIN");
            userRepository.save(newUser);
        } else {
            System.out.println("Admin user already exists, skipping creation.");
        }
    }

    /**
     * Defines the security filter chain that applies various security configurations.
     * 
     * @param http HttpSecurity configuration builder
     * @return SecurityFilterChain the configured security filter chain
     * @throws Exception in case of any misconfiguration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/css/**", "/media/**", "/", "/403", "/errorpage", "/simulateError", "/signup", "/feed", "/").permitAll()
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/admin/**", "/admin").hasRole("ADMIN")
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
