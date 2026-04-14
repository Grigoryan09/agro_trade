package am.agrotrade.core.repository;

import am.agrotrade.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByEmailIgnoreCase(String email);

    @Query(value = """
    SELECT u.id
    FROM "user" u
    WHERE u.id = (
        SELECT u2.id
        FROM "user" u2
        LEFT JOIN "order" o ON o.manager_id = u2.id
        JOIN user_roles ur ON ur.user_id = u2.id
        WHERE ur.role_name = :role
        GROUP BY u2.id
        ORDER BY COUNT(o.id) ASC
        LIMIT 1
    )
    FOR UPDATE SKIP LOCKED
""", nativeQuery = true)
    Long findFreeManagerForUpdate(@Param("role") String role);


}
