package am.agrotrade.core.repository;

import am.agrotrade.core.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    List<Organization> findAllByUserId(Long userId);

    Optional<Organization> findByIdAndUserId(Long id, Long userId);

    boolean existsByIdAndUserId(Long id, Long userId);

    boolean existsByLicenseNumber(String licenseNumber);
}
