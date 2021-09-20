package cinema;

import cinema.model.Cinema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    @Bean
    public Cinema cinema() {
        return new Cinema(9, 9);
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
