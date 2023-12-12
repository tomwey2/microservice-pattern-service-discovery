package de.tomwey2.poc.microservices.sd.a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MicroserviceA {
    @Autowired
    private DiscoveryClient client;
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceA.class, args);
    }

    @GetMapping("/greeting")
    public String greeting() {
        String name = getNameFromB();
        return String.format("Hello, %s!", name);
    }

    private String getNameFromB() {
        String service = "eureka-microservice-b";
        List<ServiceInstance> list = client.getInstances(service);
        if (list != null && list.size() > 0) {
            URI uri = list.get(0).getUri();
            if (uri != null) {
                return (new RestTemplate()).getForObject(uri, String.class);
            }
        }

        return null;
    }

}
