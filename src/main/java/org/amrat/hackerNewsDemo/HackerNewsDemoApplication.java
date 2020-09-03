package org.amrat.hackerNewsDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 *  SpringBoot App for Hacker News
 */
@SpringBootApplication
public class HackerNewsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackerNewsDemoApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


}
