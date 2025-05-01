package fast_order.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for loading environment variables.
 * This class provides access to variables defined in the .env file.
 */
@Configuration
public class EnvSetting {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().load();
    }
}
