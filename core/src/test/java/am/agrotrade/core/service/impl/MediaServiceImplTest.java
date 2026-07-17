package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.enums.EntityType;
import am.agrotrade.common.enums.Role;
import am.agrotrade.core.exception.InvalidFileException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.MediaMapper;
import am.agrotrade.core.model.Media;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.MediaRepository;
import am.agrotrade.core.repository.ProductRepository;
import am.agrotrade.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MediaServiceImplTest {

    private static final long SELLER_ID = 2L;
    private static final long PRODUCT_ID = 1L;

    @Mock
    private MediaRepository mediaRepository;
    @Mock
    private MediaMapper mediaMapper;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MediaServiceImpl mediaService;

    private void givenSellerOwnsProduct() {
        when(userRepository.findById(SELLER_ID))
                .thenReturn(Optional.of(User.builder().id(SELLER_ID).roles(List.of(Role.SELLER)).build()));
        when(productRepository.existsByIdAndSellerId(PRODUCT_ID, SELLER_ID)).thenReturn(true);
    }

    @Test
    void saveMultipleMedia_nullList_returnsEmpty() {
        givenSellerOwnsProduct();

        assertThat(mediaService.saveMultipleMedia(null, PRODUCT_ID, EntityType.PRODUCT, SELLER_ID)).isEmpty();
    }

    @Test
    void saveMultipleMedia_emptyList_returnsEmpty() {
        givenSellerOwnsProduct();

        assertThat(mediaService.saveMultipleMedia(List.of(), PRODUCT_ID, EntityType.PRODUCT, SELLER_ID)).isEmpty();
    }

    @Test
    void saveMultipleMedia_emptyFile_throwsInvalidFile() {
        givenSellerOwnsProduct();
        MultipartFile empty = new MockMultipartFile("file", "a.png", "image/png", new byte[0]);

        assertThatThrownBy(() -> mediaService.saveMultipleMedia(List.of(empty), PRODUCT_ID, EntityType.PRODUCT, SELLER_ID))
                .isInstanceOf(InvalidFileException.class);
    }

    @Test
    void saveMultipleMedia_foreignProduct_throwsAccessDenied() {
        when(userRepository.findById(SELLER_ID))
                .thenReturn(Optional.of(User.builder().id(SELLER_ID).roles(List.of(Role.SELLER)).build()));
        when(productRepository.existsByIdAndSellerId(PRODUCT_ID, SELLER_ID)).thenReturn(false);

        assertThatThrownBy(() -> mediaService.saveMultipleMedia(List.of(), PRODUCT_ID, EntityType.PRODUCT, SELLER_ID))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void saveMultipleMedia_foreignUser_throwsAccessDenied() {
        when(userRepository.findById(SELLER_ID))
                .thenReturn(Optional.of(User.builder().id(SELLER_ID).roles(List.of(Role.SELLER)).build()));

        assertThatThrownBy(() -> mediaService.saveMultipleMedia(List.of(), 99L, EntityType.USER, SELLER_ID))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void saveMultipleMedia_ownUser_allowed() {
        when(userRepository.findById(SELLER_ID))
                .thenReturn(Optional.of(User.builder().id(SELLER_ID).roles(List.of(Role.SELLER)).build()));

        assertThat(mediaService.saveMultipleMedia(List.of(), SELLER_ID, EntityType.USER, SELLER_ID)).isEmpty();
    }

    @Test
    void saveMultipleMedia_admin_skipsOwnershipCheck() {
        when(userRepository.findById(SELLER_ID))
                .thenReturn(Optional.of(User.builder().id(SELLER_ID).roles(List.of(Role.ADMIN)).build()));

        assertThat(mediaService.saveMultipleMedia(List.of(), 99L, EntityType.PRODUCT, SELLER_ID)).isEmpty();
    }

    @Test
    void findAllByEntity_delegatesToRepositoryAndMapper() {
        List<Media> entities = List.of(Media.builder().build());
        List<MediaDto> dtos = List.of(new MediaDto(1L, "a.png", "image/png", "products", 1L, "/media/1"));
        when(mediaRepository.findAllByEntityIdAndEntityType(1L, EntityType.PRODUCT)).thenReturn(entities);
        when(mediaMapper.toDtoList(entities)).thenReturn(dtos);

        assertThat(mediaService.findAllByEntity(1L, EntityType.PRODUCT)).isEqualTo(dtos);
    }

    @Test
    void loadMediaAsResource_notFound_throws() {
        when(mediaRepository.findFirstByEntityIdAndEntityTypeOrderByIdDesc(1L, EntityType.PRODUCT))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> mediaService.loadMediaAsResource(1L, EntityType.PRODUCT))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
