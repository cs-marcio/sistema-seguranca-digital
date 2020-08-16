package br.com.squadra.ssd.security;

import br.com.squadra.ssd.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        active = user.isActive();

        authorities = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
