package com.michalenok.wallet.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic topics() {
        return  TopicBuilder.name("transfer_broker")
                        .replicas(1)
                        .partitions(1)
                        .build();
    }
}