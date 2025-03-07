package com.tmsoft.runnerz;

import com.tmsoft.runnerz.run.run.JdbcRunRepository;
import com.tmsoft.runnerz.run.run.Location;
import com.tmsoft.runnerz.run.run.Run;
import com.tmsoft.runnerz.run.user.User;
import com.tmsoft.runnerz.run.user.UserHttpClient;
import com.tmsoft.runnerz.run.user.UserRestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    UserHttpClient userHttpClient() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(UserHttpClient.class);
    }

    @Bean
    CommandLineRunner runner(UserHttpClient client) {
        return args -> {
            List<User> users = client.findAll();
            System.out.println("Users: " + users);
        };
    }

}
