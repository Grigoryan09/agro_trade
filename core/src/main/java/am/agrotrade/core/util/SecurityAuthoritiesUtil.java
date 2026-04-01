package am.agrotrade.core.util;

import am.agrotrade.common.enums.Role;
import am.agrotrade.core.exception.InvalidUserRoleException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public final class SecurityAuthoritiesUtil {

    private SecurityAuthoritiesUtil() {}

    public static List<SimpleGrantedAuthority> authoritiesForRoles(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new InvalidUserRoleException("User must have at least one role assigned to generate authorities.");
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }
}
