package com.umesh.delivery_service.domain.provider.factory;

import com.umesh.delivery_service.domain.delivery.enums.DeliveryProvider;
import com.umesh.delivery_service.domain.provider.interfaces.NotificationProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProviderFactory {

    private final List<NotificationProvider> providers;

    private final Map<DeliveryProvider, NotificationProvider> providerMap = new EnumMap<>(DeliveryProvider.class);

    @PostConstruct
    void init() {

        providers.forEach(provider -> providerMap.put(
                provider.getProvider(),
                provider));

    }

    public NotificationProvider getProvider(
            DeliveryProvider provider) {

        NotificationProvider notificationProvider = providerMap.get(provider);

        if (notificationProvider == null) {

            throw new IllegalArgumentException(
                    "No provider registered for " + provider);

        }

        return notificationProvider;
    }
}