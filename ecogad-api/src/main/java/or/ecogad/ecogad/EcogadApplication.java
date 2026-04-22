package or.ecogad.ecogad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "or.ecogad.ecogad")
public class EcogadApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcogadApplication.class, args);
    }
}
