package app;

import app.config.AppConfig;
import app.loader.ProviderLoader;
import app.model.ProviderConfig;
import app.registry.ProviderRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ProviderRegistryApplication {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Path to providers JSON file is required");
        }
        Path path = Path.of(args[0]);
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProviderLoader loader = context.getBean(ProviderLoader.class);
        ProviderRegistry registry = context.getBean(ProviderRegistry.class);
        List<ProviderConfig> providerConfigList = loader.providerListFromFile(path);

        registry.replaceAll(providerConfigList);
    }
}
