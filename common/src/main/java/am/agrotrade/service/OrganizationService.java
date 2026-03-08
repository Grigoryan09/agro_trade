package am.agrotrade.service;

import am.agrotrade.model.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {

    void save(Organization organization);

    void delete(long organizationId);

    List<Organization> findAll();

    Optional<Organization> findById(long organizationId);
}
