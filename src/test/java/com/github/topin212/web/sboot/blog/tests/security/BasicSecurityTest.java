package com.github.topin212.web.sboot.blog.tests.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicSecurityTest {

    TestRestTemplate testRestTemplate;

    URL baseUrl;
    @LocalServerPort int port;

    @Before
    public void setup() throws MalformedURLException {
        testRestTemplate = new TestRestTemplate("Petya", "dGVzdHBhc3MK123");
        baseUrl = new URL("http://localhost:" + port);
    }

    @Test
    public void loginSuccessful_whenUsernameAndPasswordAreCorrect(){

        ResponseEntity response = testRestTemplate.getForEntity(
                baseUrl.toString() + "/posts/all", String.class);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isNotEqualTo("");
        assertThat(response.getBody()).isNotEqualTo("").isNotEqualTo(null);
    }


    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage()
            throws Exception {

        testRestTemplate = new TestRestTemplate("Petya", "wrong_dGVzdHBhc3MK");

        ResponseEntity<String> response
                = testRestTemplate.getForEntity(baseUrl.toString(), String.class);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.UNAUTHORIZED);

        assertThat(response.getBody()).isEqualToIgnoringCase("fail");
    }

    @After
    public void tearDown(){

    }
}
