package hac.ex5.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html#authentication-password-storage-dep-getting-started
public class PasswordConfig {

        @Bean
        PasswordEncoder getpasswordEncoder()
        {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
}
