package app.loader;

import app.model.ProviderConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
public class ProviderLoader {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ProviderConfig> providerListFromFile(Path path) throws IOException {
        return objectMapper.readValue(path.toFile(), new TypeReference<>() {
        });
    }
}
