package com.simondudley.example;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SingleSignOnUser implements UserDetails {

    private final String authHeaderValue;

    public static SingleSignOnUser fromPrincipal(Object principal) {
        return new SingleSignOnUser(principal.toString());
    }

    private SingleSignOnUser(String authHeaderValue) {
        this.authHeaderValue = String.valueOf(authHeaderValue);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public String getPassword() {
        return "N/a";
    }

    @Override
    public String getUsername() {
        return authHeaderValue;
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
        return true;
    }
}
