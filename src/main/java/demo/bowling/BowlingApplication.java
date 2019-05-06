package demo.bowling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BowlingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BowlingApplication.class, args);
    }

}
