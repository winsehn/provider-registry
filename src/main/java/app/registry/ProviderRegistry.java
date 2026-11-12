package app.registry;

import app.model.ProviderConfig;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProviderRegistry {
    private Map<String, ProviderConfig> providers = new HashMap<>();

    public void replaceAll(List<ProviderConfig> providersList) {
        if (providersList == null || providersList.isEmpty()) {
            providers = new HashMap<>();
            return;
        }
        providers = providersList.stream()
                .collect(Collectors.toMap((ProviderConfig::id), p -> p));
    }

    public ProviderConfig getById(String providerId) {
        ProviderConfig providerConfig = providers.get(providerId);
        if (providerConfig == null) {
            throw new IllegalArgumentException("Provider not found: " + providerId);
        }
        return providerConfig;
    }
}
