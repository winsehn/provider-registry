package app.service;

import app.client.ProviderHttpClient;
import app.model.ProviderConfig;
import app.registry.ProviderRegistry;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {
    private final ProviderRegistry providerRegistry;
    private final ProviderHttpClient providerHttpClient;

    public ProviderService(ProviderRegistry providerRegistry, ProviderHttpClient providerHttpClient) {
        this.providerRegistry = providerRegistry;
        this.providerHttpClient = providerHttpClient;
    }

    public boolean isPhoneAvailable(String providerId, String phone) {
        ProviderConfig providerConfig = providerRegistry.getById(providerId);
        return providerHttpClient.isPhoneAvailable(providerConfig, phone);
    }
}
