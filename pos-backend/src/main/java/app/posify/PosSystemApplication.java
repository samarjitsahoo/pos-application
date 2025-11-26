package app.posify;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosSystemApplication {

    public static void main(String[] args) {

        // Load .env variables
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // Set Spring environment properties
        System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
        System.setProperty("spring.datasource.driver-class-name", dotenv.get("DB_DRIVER"));

        SpringApplication.run(PosSystemApplication.class, args);
    }
}
