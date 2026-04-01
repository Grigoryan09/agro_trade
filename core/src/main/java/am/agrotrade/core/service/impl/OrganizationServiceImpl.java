package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;
import am.agrotrade.core.exception.AlreadyExistsException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.OrganizationMapper;
import am.agrotrade.core.model.Organization;
import am.agrotrade.core.repository.OrganizationRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.OrganizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    public List<OrganizationDetailsDto> getAllByUserId(long userId) {
        List<Organization> organizations = organizationRepository.findAllByUserId(userId);

        if (organizations.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No organizations found for user ID: %d".formatted(userId)
            );
        }

        return organizationMapper.toDtoList(organizations);
    }

    @Override
    public OrganizationDetailsDto getById(long userId, long organizationId) {
        Organization org = organizationRepository.findByIdAndUserId(organizationId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found for ID: %d and user ID: %d".formatted(organizationId, userId)
                ));
        return organizationMapper.toDto(org);
    }

    @Override
    @Transactional
    public OrganizationDetailsDto create(long userId, CreateOrganizationRequest request) {
        if (organizationRepository.existsByLicenseNumber(request.licenseNumber())) {
            throw new AlreadyExistsException("Organization with this license number already exists.");
        }

        Organization organization = organizationMapper.toEntity(request);

        organization.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));

        return organizationMapper.toDto(organizationRepository.save(organization));
    }

    @Override
    @Transactional
    public OrganizationDetailsDto update(long userId, long organizationId, UpdateOrganizationRequest request) {
        Organization existingOrg =
                organizationRepository.findByIdAndUserId(request.organizationId(), userId)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Organization not found or access denied"));

        organizationMapper.updateOrganizationFromRequest(request,existingOrg);

        return organizationMapper.toDto(organizationRepository.save(existingOrg));
    }

    @Override
    @Transactional
    public void delete(long orgId, long userId) {
        if (!organizationRepository.existsByIdAndUserId(orgId, userId)) {
            throw new ResourceNotFoundException("Cannot delete: Organization not found or access denied");
        }
        organizationRepository.deleteById(orgId);
    }
}