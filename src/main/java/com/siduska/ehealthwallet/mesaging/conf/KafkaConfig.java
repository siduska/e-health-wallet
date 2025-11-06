package com.siduska.ehealthwallet.mesaging.conf;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.properties.sasl.jaas.config:}")
    private String jaasConfig;

    @PostConstruct
    public void init() {
        System.out.println(">>> Kafka bootstrapServers property: " + bootstrapServers);
        System.out.println(">>> Kafka JAAS config loaded: " + (jaasConfig != null && !jaasConfig.contains("${")));
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        if (!jaasConfig.isEmpty()) {
            config.put("security.protocol", "SASL_SSL");
            config.put("sasl.mechanism", "PLAIN");
            config.put("sasl.jaas.config", jaasConfig);
        }

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic reimbursementStatusChangedTopic() {
        return TopicBuilder.name("reimbursement-status-changed")
                .partitions(1)
                .replicas(1)
                .build();
    }
}