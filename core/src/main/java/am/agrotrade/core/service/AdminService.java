package am.agrotrade.core.service;

import am.agrotrade.common.dto.news.BaseNewsInfoDto;
import am.agrotrade.common.dto.order.OrderDetailsDto;
import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.passport.PassportInfoDto;
import am.agrotrade.common.dto.product.ProductInfoDto;
import am.agrotrade.common.dto.user.UserForAdminDto;
import am.agrotrade.common.enums.OrderStatus;
import am.agrotrade.common.enums.Role;
import org.springframework.data.domain.Pageable;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Provides system-wide oversight operations available only to administrators.
 * Implementations are responsible for cross-user access to orders, users,
 * products, news, organizations and passport data.
 */
public interface AdminService {

    /**
     * Returns every order in the system, optionally filtered by status.
     *
     * @param status optional status filter; {@code null} returns all statuses
     * @param pageable paging parameters
     * @return order list
     */
    List<OrderDetailsDto> findAllOrders(@Nullable OrderStatus status, Pageable pageable);

    /**
     * Soft-deletes any order by setting its status to {@link OrderStatus#DELETED}.
     *
     * @param orderId order identifier
     */
    void deleteOrder(long orderId);

    /**
     * Returns all users, optionally filtered by role and a free-text search
     * over username, email, name and surname.
     *
     * @param role optional role filter; {@code null} returns all roles
     * @param search optional search term; {@code null} or blank returns all users
     * @param pageable paging parameters
     * @return user list (without sensitive fields)
     */
    List<UserForAdminDto> findAllUsers(@Nullable Role role, @Nullable String search, Pageable pageable);

    /**
     * Returns a single user by identifier.
     *
     * @param userId user identifier
     * @return user data (without sensitive fields)
     */
    UserForAdminDto findUserById(long userId);

    /**
     * Replaces the roles assigned to a user.
     *
     * @param userId user identifier
     * @param roles new role list
     * @return updated user data
     */
    UserForAdminDto updateUserRoles(long userId, List<Role> roles);

    /**
     * Enables or disables (and thereby verifies) a user account.
     *
     * @param userId user identifier
     * @param active new active flag
     * @return updated user data
     */
    UserForAdminDto updateUserStatus(long userId, boolean active);

    /**
     * Returns every product in the system regardless of owner or status.
     *
     * @param pageable paging parameters
     * @return product list
     */
    List<ProductInfoDto> findAllProducts(Pageable pageable);

    /**
     * Soft-deletes any product by setting its status to deleted.
     *
     * @param productId product identifier
     */
    void deleteProduct(long productId);

    /**
     * Returns every news entry in the system.
     *
     * @param pageable paging parameters
     * @return news list
     */
    List<BaseNewsInfoDto> findAllNews(Pageable pageable);

    /**
     * Deletes any news entry.
     *
     * @param newsId news identifier
     */
    void deleteNews(long newsId);

    /**
     * Returns every organization in the system.
     *
     * @param pageable paging parameters
     * @return organization list
     */
    List<OrganizationDetailsDto> findAllOrganizations(Pageable pageable);

    /**
     * Returns the passport data of the specified user.
     *
     * @param userId user identifier
     * @return passport data
     */
    PassportInfoDto findPassportByUserId(long userId);
}
