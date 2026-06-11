package or.ecogod.ecogod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "or.ecogod.ecogod")
public class EcogodApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcogodApplication.class, args);
    }
}
