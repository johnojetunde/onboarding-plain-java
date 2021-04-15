package com.telesoftas.onboarding.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExampleTest {
    @Test
    void lombok_works() {
        Version version = new Version("1.0.0");

        assertEquals("1.0.0", version.getVersion());
    }

    @Test
    void mockito_works() {
       Version u = mock(Version.class);
        when(u.getVersion()).thenReturn("1.0.0");

        assertEquals("1.0.0", u.getVersion());
    }

    @Test
    void http_client_works() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("https://telesoftas.com");

        CloseableHttpResponse response = httpClient.execute(httpget);
        response.close();

        assertEquals(200, response.getStatusLine().getStatusCode());
    }
}


@Getter @AllArgsConstructor
class Version {
    private String version;
}
