package app.client;

import app.model.ProviderConfig;
import app.response.ProviderStatus;
import app.response.ProviderStatusResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class ProviderHttpClient {

    private final RestClient restClient;

    public ProviderHttpClient(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public boolean isPhoneAvailable(ProviderConfig provider, String phone) {
        try {
            ProviderStatusResponse response = restClient.get()
                    .uri(provider.baseUrl() + "/subscribers/{phone}", phone)
                    .header("X-API-Key", provider.apiKey())
                    .retrieve()
                    .body(ProviderStatusResponse.class);

            return response.status() == ProviderStatus.OK;

        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}