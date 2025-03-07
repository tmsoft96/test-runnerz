package com.tmsoft.runnerz.run.user;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest(UserRestClient.class)
public class UserRestClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    UserRestClient userRestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldFindAllUsers() throws JsonProcessingException {
        // given
        User user1 = new User(1,
                "Michael",
                "tmx",
                "michaeltamekloe18.mt@gmail.com",
                new Address(
                        "Kulas Light",
                        "Apt. 444",
                        "Accra",
                        "233", new Geo(
                                "0",
                                "0")),
                "0554302776",
                "google.com",
                new Company(
                        "TMSoft",
                        "Multi-layered client server",
                        "harness real-time e-markets"));

        List<User> users = List.of(user1);

        // when
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(users), MediaType.APPLICATION_JSON));

        // then
        List<User> alluser = userRestClient.findAll();
        assertEquals(users, alluser);
    }

    @Test
    void shouldFindUserById() throws JsonProcessingException {
        // given
        User user = new User(1,
                "Michael",
                "tmx",
                "michaeltamekloe18.mt@gmail.com",
                new Address(
                        "Kulas Light",
                        "Apt. 444",
                        "Accra",
                        "233", new Geo(
                                "0",
                                "0")),
                "0554302776",
                "google.com",
                new Company(
                        "TMSoft",
                        "Multi-layered client server",
                        "harness real-time e-markets"));

        // when
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users/1"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(user), MediaType.APPLICATION_JSON));

        // then
        User userById = userRestClient.findById(1);
        assertEquals(user.name(), "Michael", "Name should be Michael");
        assertEquals(user.username(), "tmx", "Username should be tmx");
        assertEquals(user.email(), "michaeltamekloe18.mt@gmail.com", "Email should be michaeltamekloe18.mt@gmail.com");
        assertEquals(user.phone(), "0554302776", "Phone should be 0554302776");
        assertEquals(user.website(), "google.com", "Website should be google.com");
        assertAll("Address",
                () -> assertEquals(user.address().street(), "Kulas Light", "Street should be Kulas Light"),
                () -> assertEquals(user.address().suite(), "Apt. 444", "Suite should be Apt. 444"),
                () -> assertEquals(user.address().city(), "Accra", "City should be Accra"),
                () -> assertEquals(user.address().zipcode(), "233", "Zipcode should be 233"),
                () -> assertAll("Geo",
                        () -> assertEquals(user.address().geo().lat(), "0", "Lat should be 0"),
                        () -> assertEquals(user.address().geo().lng(), "0", "Lng should be 0")
                )
        );
        assertAll("Company",
                () -> assertEquals(user.company().name(), "TMSoft", "Name should be TMSoft"),
                () -> assertEquals(user.company().catchPhrase(), "Multi-layered client server", "CatchPhrase should be Multi-layered client server"),
                () -> assertEquals(user.company().bs(), "harness real-time e-markets", "BS should be harness real-time e-markets")
        );
        assertEquals(user, userById);
    }

}
