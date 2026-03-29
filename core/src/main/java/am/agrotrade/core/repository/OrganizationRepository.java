package am.agrotrade.core.repository;

import am.agrotrade.core.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
