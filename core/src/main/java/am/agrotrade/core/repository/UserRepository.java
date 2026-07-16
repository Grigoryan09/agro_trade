package am.agrotrade.core.repository;

import am.agrotrade.common.enums.Role;
import am.agrotrade.core.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByEmailIgnoreCase(String email);

    @Query("""
                SELECT u
                FROM User u
                JOIN u.roles r
                WHERE r = :role
                ORDER BY u.activeOrdersCount ASC
            """)
    List<User> findCandidatesForUpdate(@Param("role") Role role, Pageable pageable);

    @Modifying
    @Query("""
                UPDATE User u
                SET u.activeOrdersCount = u.activeOrdersCount + 1
                WHERE u.id = :id
            """)
    void incrementOrders(@Param("id") Long id);

    @Query(value = """
                SELECT DISTINCT u
                FROM User u
                LEFT JOIN u.roles r
                WHERE (:role IS NULL OR r = :role)
                AND (:search IS NULL OR :search = ''
                     OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :search, '%')))
            """,
            countQuery = """
                SELECT COUNT(DISTINCT u)
                FROM User u
                LEFT JOIN u.roles r
                WHERE (:role IS NULL OR r = :role)
                AND (:search IS NULL OR :search = ''
                     OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<User> searchForAdmin(@Param("role") Role role,
                              @Param("search") String search,
                              Pageable pageable);
}
