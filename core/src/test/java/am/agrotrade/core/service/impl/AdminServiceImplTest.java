package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.passport.PassportInfoDto;
import am.agrotrade.common.dto.user.UserForAdminDto;
import am.agrotrade.common.enums.OrderStatus;
import am.agrotrade.common.enums.ProductStatus;
import am.agrotrade.common.enums.Role;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.NewsMapper;
import am.agrotrade.core.mapper.OrderMapper;
import am.agrotrade.core.mapper.OrganizationMapper;
import am.agrotrade.core.mapper.ProductMapper;
import am.agrotrade.core.mapper.UserMapper;
import am.agrotrade.core.model.Order;
import am.agrotrade.core.model.Product;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.NewsRepository;
import am.agrotrade.core.repository.OrderRepository;
import am.agrotrade.core.repository.OrganizationRepository;
import am.agrotrade.core.repository.ProductRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.PassportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private NewsMapper newsMapper;
    @Mock
    private OrganizationMapper organizationMapper;
    @Mock
    private PassportService passportService;

    @InjectMocks
    private AdminServiceImpl adminService;

    private final Pageable pageable = PageRequest.of(0, 10);

    @Test
    void findAllOrders_nullStatus_usesFindAll() {
        when(orderRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(new Order())));
        when(orderMapper.toDto(any())).thenReturn(null);

        adminService.findAllOrders(null, pageable);

        verify(orderRepository).findAll(pageable);
        verify(orderRepository, never()).findAllByOrderStatus(any(), any());
    }

    @Test
    void findAllOrders_withStatus_filtersByStatus() {
        when(orderRepository.findAllByOrderStatus(OrderStatus.COMPLETED, pageable))
                .thenReturn(new PageImpl<>(List.of()));

        adminService.findAllOrders(OrderStatus.COMPLETED, pageable);

        verify(orderRepository).findAllByOrderStatus(OrderStatus.COMPLETED, pageable);
        verify(orderRepository, never()).findAll(pageable);
    }

    @Test
    void deleteOrder_softDeletes() {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PROCESSING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        adminService.deleteOrder(1L);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.DELETED);
    }

    @Test
    void deleteOrder_notFound_throws() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.deleteOrder(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findAllUsers_delegatesToSearch() {
        Page<User> page = new PageImpl<>(List.of(new User()));
        when(userRepository.searchForAdmin(eq(Role.MANAGER), eq("john"), eq(pageable))).thenReturn(page);
        when(userMapper.toUserForAdminDto(any())).thenReturn(mock());

        List<UserForAdminDto> result = adminService.findAllUsers(Role.MANAGER, "john", pageable);

        assertThat(result).hasSize(1);
    }

    @Test
    void findUserById_notFound_throws() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.findUserById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateUserRoles_setsRoles() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserForAdminDto(user)).thenReturn(mock());

        adminService.updateUserRoles(1L, List.of(Role.ADMIN));

        assertThat(user.getRoles()).containsExactly(Role.ADMIN);
    }

    @Test
    void updateUserStatus_setsActiveFlag() {
        User user = new User();
        user.setActive(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserForAdminDto(user)).thenReturn(mock());

        adminService.updateUserStatus(1L, false);

        assertThat(user.isActive()).isFalse();
    }

    @Test
    void deleteProduct_softDeletes() {
        Product product = new Product();
        product.setStatus(ProductStatus.AVAILABLE);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        adminService.deleteProduct(1L);

        assertThat(product.getStatus()).isEqualTo(ProductStatus.DELETED);
    }

    @Test
    void deleteNews_missing_throws() {
        when(newsRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> adminService.deleteNews(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(newsRepository, never()).deleteById(any());
    }

    @Test
    void deleteNews_existing_deletes() {
        when(newsRepository.existsById(1L)).thenReturn(true);

        adminService.deleteNews(1L);

        verify(newsRepository).deleteById(1L);
    }

    @Test
    void findPassportByUserId_delegatesToPassportService() {
        PassportInfoDto dto = org.mockito.Mockito.mock(PassportInfoDto.class);
        when(passportService.getPassport(1L)).thenReturn(dto);

        assertThat(adminService.findPassportByUserId(1L)).isSameAs(dto);
    }

    private static UserForAdminDto mock() {
        return org.mockito.Mockito.mock(UserForAdminDto.class);
    }
}
