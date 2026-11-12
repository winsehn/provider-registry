package app.client;

import app.model.ProviderConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class ProviderHttpClientTest {
    private MockRestServiceServer mockServer;
    private ProviderHttpClient providerHttpClient;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();

        mockServer = MockRestServiceServer.bindTo(builder).build();

        providerHttpClient = new ProviderHttpClient(builder);
    }

    @Test
    void shouldReturnTrueWhenProviderReturnsOk() {
        ProviderConfig provider = new ProviderConfig(
                "provider-1",
                "https://provider-1.com",
                "providers1ApiKey"
        );

        mockServer.expect(requestTo(
                        "https://provider-1.com/subscribers/79198647845"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-API-Key", "providers1ApiKey"))
                .andRespond(withSuccess(
                        "{\"status\":\"ok\"}",
                        MediaType.APPLICATION_JSON
                ));

        boolean result = providerHttpClient.isPhoneAvailable(
                provider,
                "79198647845"
        );

        assertTrue(result);
        mockServer.verify();
    }

    @Test
    void shouldReturnFalseWhenProviderReturnsNotFound() {
        ProviderConfig provider = new ProviderConfig(
                "provider-2",
                "https://provider-2.com",
                "providers2ApiKey"
        );

        mockServer.expect(requestTo(
                        "https://provider-2.com/subscribers/79105798463"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-API-Key", "providers2ApiKey"))
                .andRespond(withStatus(
                        org.springframework.http.HttpStatus.NOT_FOUND
                ));

        boolean result = providerHttpClient.isPhoneAvailable(
                provider,
                "79105798463"
        );

        assertFalse(result);
        mockServer.verify();
    }
}

