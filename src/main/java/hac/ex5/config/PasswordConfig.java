package hac.ex5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration for password encoding in Spring Security.
 * It defines a PasswordEncoder bean using the recommended delegating password encoder that automatically selects a strong password encoding mechanism.
 */
@Configuration
public class PasswordConfig {

    /**
     * Creates a PasswordEncoder that uses a delegating password encoder from Spring Security.
     * This encoder supports various encoding methods and automatically handles id storage to manage multiple encoding formats.
     *
     * @return PasswordEncoder an instance of PasswordEncoder that can encode passwords securely.
     */
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
