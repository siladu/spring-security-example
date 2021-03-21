package com.simondudley.example;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Optional;

public class SingleSignOnUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        return Optional.ofNullable(token.getPrincipal())
                .map(SingleSignOnUser::fromPrincipal)
                .orElse(null);
        }
}
