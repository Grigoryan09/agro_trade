package am.agrotrade.core.util;

import am.agrotrade.common.dto.banking.event.ContractCreateResultEvent;
import am.agrotrade.common.dto.document.OrderDocumentRequestEvent;
import am.agrotrade.common.dto.document.OrderDocumentResultEvent;
import am.agrotrade.common.dto.document.contract.ContractDocumentResultEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:agro-trade}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset:earliest}")
    private String autoOffsetReset;

    private <T> Map<String, Object> baseConfig(Class<T> targetType) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        config.put(JacksonJsonDeserializer.VALUE_DEFAULT_TYPE, targetType.getName());
        config.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "am.agrotrade.common.*");
        config.put(JacksonJsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return config;
    }

    @Bean
    public ConsumerFactory<String, OrderDocumentRequestEvent> orderDocumentRequestConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseConfig(OrderDocumentRequestEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderDocumentRequestEvent> orderDocumentRequestKafkaListenerContainerFactory(
            ConsumerFactory<String, OrderDocumentRequestEvent> orderDocumentRequestConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, OrderDocumentRequestEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderDocumentRequestConsumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OrderDocumentResultEvent> orderDocumentResultConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseConfig(OrderDocumentResultEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderDocumentResultEvent> orderDocumentResultKafkaListenerContainerFactory(
            ConsumerFactory<String, OrderDocumentResultEvent> orderDocumentResultConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, OrderDocumentResultEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderDocumentResultConsumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ContractCreateResultEvent> contractCreateResultConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseConfig(ContractCreateResultEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ContractCreateResultEvent> contractCreateResultKafkaListenerContainerFactory(
            ConsumerFactory<String, ContractCreateResultEvent> contractCreateResultConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, ContractCreateResultEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(contractCreateResultConsumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ContractDocumentResultEvent> contractDocumentResultConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseConfig(ContractDocumentResultEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ContractDocumentResultEvent> contractDocumentResultKafkaListenerContainerFactory(
            ConsumerFactory<String, ContractDocumentResultEvent> contractDocumentResultConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, ContractDocumentResultEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(contractDocumentResultConsumerFactory);
        return factory;
    }
}
