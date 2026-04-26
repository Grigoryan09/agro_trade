package am.agrotrade.core.repository;

import am.agrotrade.common.enums.Role;
import am.agrotrade.core.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
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
}
