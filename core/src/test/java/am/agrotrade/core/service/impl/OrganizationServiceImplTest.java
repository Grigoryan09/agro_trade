package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;
import am.agrotrade.core.exception.AlreadyExistsException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.OrganizationMapper;
import am.agrotrade.core.model.Organization;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.OrganizationRepository;
import am.agrotrade.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {

    private static final long USER_ID = 1L;
    private static final long ORG_ID = 9L;

    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrganizationMapper organizationMapper;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    private CreateOrganizationRequest createRequest() {
        return new CreateOrganizationRequest("Agro LLC", "LIC12345", "Yerevan", "+37411111111", "agro@mail.com");
    }

    private UpdateOrganizationRequest updateRequest() {
        return new UpdateOrganizationRequest(ORG_ID, "Agro LLC", "LIC12345", "Yerevan", "+37411111111", "agro@mail.com");
    }

    @Test
    void getAllByUserId_empty_throws() {
        when(organizationRepository.findAllByUserId(USER_ID)).thenReturn(List.of());

        assertThatThrownBy(() -> organizationService.getAllByUserId(USER_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById_notFound_throws() {
        when(organizationRepository.findByIdAndUserId(ORG_ID, USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> organizationService.getById(USER_ID, ORG_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_duplicateLicense_throws() {
        when(organizationRepository.existsByLicenseNumber("LIC12345")).thenReturn(true);

        assertThatThrownBy(() -> organizationService.create(USER_ID, createRequest()))
                .isInstanceOf(AlreadyExistsException.class);

        verify(organizationRepository, never()).save(any());
    }

    @Test
    void create_userNotFound_throws() {
        Organization org = new Organization();
        when(organizationRepository.existsByLicenseNumber("LIC12345")).thenReturn(false);
        when(organizationMapper.toEntity(any(CreateOrganizationRequest.class))).thenReturn(org);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> organizationService.create(USER_ID, createRequest()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_success_setsUserAndPersists() {
        Organization org = new Organization();
        User user = new User();
        user.setId(USER_ID);
        OrganizationDetailsDto dto = new OrganizationDetailsDto("Agro LLC", "LIC12345", "Yerevan", "+37411111111", "agro@mail.com", null);
        when(organizationRepository.existsByLicenseNumber("LIC12345")).thenReturn(false);
        when(organizationMapper.toEntity(any(CreateOrganizationRequest.class))).thenReturn(org);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(organizationRepository.save(org)).thenReturn(org);
        when(organizationMapper.toDto(org)).thenReturn(dto);

        organizationService.create(USER_ID, createRequest());

        verify(organizationRepository).save(org);
        assertThat(org.getUser()).isSameAs(user);
    }

    @Test
    void update_notFound_throws() {
        when(organizationRepository.findByIdAndUserId(ORG_ID, USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> organizationService.update(USER_ID, ORG_ID, updateRequest()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_notFound_throws() {
        when(organizationRepository.existsByIdAndUserId(ORG_ID, USER_ID)).thenReturn(false);

        assertThatThrownBy(() -> organizationService.delete(ORG_ID, USER_ID))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(organizationRepository, never()).deleteById(any());
    }

    @Test
    void delete_existing_deletesById() {
        when(organizationRepository.existsByIdAndUserId(ORG_ID, USER_ID)).thenReturn(true);

        organizationService.delete(ORG_ID, USER_ID);

        verify(organizationRepository).deleteById(ORG_ID);
    }
}
