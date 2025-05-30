package fast_order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "NOTIFICATION")
@Table(name = "NOTIFICATIONS", schema = "fast_order_schema")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "notification_id", unique = true)
    private UUID id;
    
    @NotNull(message = "{field.null}")
    @Column(name = "message", updatable = false)
    private String message;
    
    @NotNull(message = "{field.null}")
    @Column(name = "order_id", updatable = false)
    private UUID orderId;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
