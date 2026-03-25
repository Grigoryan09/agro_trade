package am.agrotrade.core.util;

import am.agrotrade.common.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public final class SecurityAuthoritiesUtil {

    private SecurityAuthoritiesUtil() {}

    public static List<SimpleGrantedAuthority> authoritiesForRoles(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Roles must not be empty");
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }
}
