package am.agrotrade.core.model;

import am.agrotrade.common.enums.EntityType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String url;

    private String filePath;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    private long entityId;

    private LocalDateTime createdAt;


}
