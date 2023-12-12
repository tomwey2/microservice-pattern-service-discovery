package de.tomwey2.poc.microservices.sd.b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MicroserviceB {
    private final List<String> names = List.of("John Doe", "Jane Dee", "Max Mustermann");
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceB.class, args);
    }

    @GetMapping("/")
    public String greeting() {
        Random random = new Random();
        return names.get(random.nextInt(3));
    }
}
