package com.michalenok.wallet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfiguration {
    @Bean
    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("debit_operation_topic")
                        .replicas(1)
                        .partitions(1)
                        .build(),
                TopicBuilder.name("credit_operation_topic")
                        .replicas(1)
                        .partitions(1)
                        .build(),
                TopicBuilder.name("internal_operation_topic")
                        .replicas(1)
                        .partitions(1)
                        .build());
    }
}