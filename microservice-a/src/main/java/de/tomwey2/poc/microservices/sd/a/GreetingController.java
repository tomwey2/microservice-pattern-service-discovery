package de.tomwey2.poc.microservices.sd.a;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class GreetingController {

    private final DiscoveryClient client;

    @Autowired
    public GreetingController(DiscoveryClient client) {
        this.client = client;
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
                log.info("Request service: " + service + " with uri: " + uri.toString());
                return (new RestTemplate()).getForObject(uri, String.class);
            }
        }

        return null;
    }
}
