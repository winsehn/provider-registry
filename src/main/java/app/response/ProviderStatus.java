package app.response;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProviderStatus {
    OK;

    @JsonCreator
    public static ProviderStatus fromValue(String value) {
        return ProviderStatus.valueOf(value.toUpperCase());
    }
    }
