package am.agrotrade.model.entity;

import am.agrotrade.model.enums.EntityType;
import jakarta.persistence.*;
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
