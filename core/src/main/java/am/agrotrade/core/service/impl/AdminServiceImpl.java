package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.news.BaseNewsInfoDto;
import am.agrotrade.common.dto.order.OrderDetailsDto;
import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.passport.PassportInfoDto;
import am.agrotrade.common.dto.product.ProductInfoDto;
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
import am.agrotrade.core.service.AdminService;
import am.agrotrade.core.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final NewsRepository newsRepository;
    private final OrganizationRepository organizationRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final NewsMapper newsMapper;
    private final OrganizationMapper organizationMapper;
    private final PassportService passportService;

    @Override
    public List<OrderDetailsDto> findAllOrders(@Nullable OrderStatus status, Pageable pageable) {
        Page<Order> orders = (status == null)
                ? orderRepository.findAll(pageable)
                : orderRepository.findAllByOrderStatus(status, pageable);
        return orders.map(orderMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteOrder(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found for ID: %d ".formatted(orderId)
                ));
        order.setOrderStatus(OrderStatus.DELETED);
    }

    @Override
    public List<UserForAdminDto> findAllUsers(@Nullable Role role, @Nullable String search, Pageable pageable) {
        return userRepository.searchForAdmin(role, search, pageable)
                .map(userMapper::toUserForAdminDto)
                .toList();
    }

    @Override
    public UserForAdminDto findUserById(long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toUserForAdminDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found for ID: %d".formatted(userId)
                ));
    }

    @Override
    @Transactional
    public UserForAdminDto updateUserRoles(long userId, List<Role> roles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found for ID: %d".formatted(userId)
                ));
        user.setRoles(roles);
        return userMapper.toUserForAdminDto(user);
    }

    @Override
    @Transactional
    public UserForAdminDto updateUserStatus(long userId, boolean active) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found for ID: %d".formatted(userId)
                ));
        user.setActive(active);
        return userMapper.toUserForAdminDto(user);
    }

    @Override
    public List<ProductInfoDto> findAllProducts(Pageable pageable) {
        return productRepository.findAllByStatusNot(ProductStatus.DELETED, pageable)
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found for ID: %d ".formatted(productId)
                ));
        product.setStatus(ProductStatus.DELETED);
    }

    @Override
    public List<BaseNewsInfoDto> findAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(newsMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteNews(long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new ResourceNotFoundException("News not found for ID: %d".formatted(newsId));
        }
        newsRepository.deleteById(newsId);
    }

    @Override
    public List<OrganizationDetailsDto> findAllOrganizations(Pageable pageable) {
        return organizationRepository.findAll(pageable)
                .map(organizationMapper::toDto)
                .toList();
    }

    @Override
    public PassportInfoDto findPassportByUserId(long userId) {
        return passportService.getPassport(userId);
    }
}
