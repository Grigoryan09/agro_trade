package am.agrotrade.core.security;

import am.agrotrade.core.model.User;
import am.agrotrade.core.util.SecurityAuthoritiesUtil;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record UserPrincipal(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return SecurityAuthoritiesUtil.authoritiesForRoles(user.getRoles());
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public long getId() {
        return user.getId();
    }
}
