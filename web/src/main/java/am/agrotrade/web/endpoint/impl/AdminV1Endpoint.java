package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.news.response.NewsResponse;
import am.agrotrade.common.dto.order.response.OrderResponse;
import am.agrotrade.common.dto.organization.response.OrganizationDetailsResponse;
import am.agrotrade.common.dto.passport.response.PassportInfoResponse;
import am.agrotrade.common.dto.product.response.ProductInfoResponse;
import am.agrotrade.common.dto.user.request.UpdateUserRolesRequest;
import am.agrotrade.common.dto.user.request.UpdateUserStatusRequest;
import am.agrotrade.common.dto.user.response.UsersForAdminResponse;
import am.agrotrade.common.enums.OrderStatus;
import am.agrotrade.common.enums.Role;
import am.agrotrade.core.service.AdminService;
import am.agrotrade.web.endpoint.AdminV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminV1Endpoint implements AdminV1API {

    private final AdminService adminService;

    @Override
    public OrderResponse getAllOrders(OrderStatus orderStatus, Pageable pageable) {
        return new OrderResponse(adminService.findAllOrders(orderStatus, pageable));
    }

    @Override
    public void deleteOrder(long id) {
        adminService.deleteOrder(id);
    }

    @Override
    public UsersForAdminResponse getAllUsers(Role role, String search, Pageable pageable) {
        return new UsersForAdminResponse(adminService.findAllUsers(role, search, pageable));
    }

    @Override
    public UsersForAdminResponse getUserById(long id) {
        return new UsersForAdminResponse(List.of(adminService.findUserById(id)));
    }

    @Override
    public UsersForAdminResponse updateUserRoles(long id, UpdateUserRolesRequest request) {
        return new UsersForAdminResponse(List.of(adminService.updateUserRoles(id, request.roles())));
    }

    @Override
    public UsersForAdminResponse updateUserStatus(long id, UpdateUserStatusRequest request) {
        return new UsersForAdminResponse(List.of(adminService.updateUserStatus(id, request.active())));
    }

    @Override
    public PassportInfoResponse getUserPassport(long id) {
        return new PassportInfoResponse(List.of(adminService.findPassportByUserId(id)));
    }

    @Override
    public ProductInfoResponse getAllProducts(Pageable pageable) {
        return new ProductInfoResponse(adminService.findAllProducts(pageable));
    }

    @Override
    public void deleteProduct(long id) {
        adminService.deleteProduct(id);
    }

    @Override
    public NewsResponse getAllNews(Pageable pageable) {
        return new NewsResponse(adminService.findAllNews(pageable));
    }

    @Override
    public void deleteNews(long id) {
        adminService.deleteNews(id);
    }

    @Override
    public OrganizationDetailsResponse getAllOrganizations(Pageable pageable) {
        return new OrganizationDetailsResponse(adminService.findAllOrganizations(pageable));
    }
}
