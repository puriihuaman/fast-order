package fast_order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value("${TIME_EXPIRATION}")
    private String timeExpiration;
    
    @Value("${KAFKA_TOPIC_NAME}")
    private String topicName;
    
    @Bean
    public NewTopic topic() {
        Map<String, String> topicConfig = new HashMap<>();
        
        topicConfig.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        
        /*
         * Message retention time.
         */
        topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, timeExpiration);
        
        /*
         * Maximum segment size.
         * Default: 1GB
         */
        topicConfig.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824");
        
        /*
         * Maximum size of each message.
         * Default: 1MB
         */
        topicConfig.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1048576");
        
        return TopicBuilder.name(topicName).partitions(2).replicas(2).configs(topicConfig).build();
    }
}
