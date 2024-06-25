package hac.ex5.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Configuration for message resources in a Spring application.
 * This allows for internationalization by setting up a message source that can reload changes without restarting the application.
 */
@Configuration
public class MessageConfig {

    /**
     * Bean definition for the message source.
     * It sets up a ReloadableResourceBundleMessageSource that points to resource bundles in the classpath under 'content' directory.
     * The message source uses UTF-8 encoding for character decoding.
     *
     * @return MessageSource configured for reloading resources with UTF-8 encoding.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:content");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
