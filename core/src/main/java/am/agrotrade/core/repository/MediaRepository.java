package am.agrotrade.core.repository;

import am.agrotrade.common.enums.EntityType;
import am.agrotrade.core.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findAllByEntityIdAndEntityType(long entityId, EntityType entityType);

    Optional<Media> findFirstByEntityIdAndEntityTypeOrderByIdDesc(long entityId, EntityType entityType);
}
