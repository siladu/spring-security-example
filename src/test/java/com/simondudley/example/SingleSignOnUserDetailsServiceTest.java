package com.simondudley.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleSignOnUserDetailsServiceTest {

    private SingleSignOnUserDetailsService service;

    @BeforeEach
    void init() {
        service = new SingleSignOnUserDetailsService();
    }

    @Test
    public void loadsSingleSignOnUserFromPrincipal() {
        // Given
        PreAuthenticatedAuthenticationToken preAuthToken = new PreAuthenticatedAuthenticationToken("ssoUser", "N/a");

        // When
        final UserDetails userDetails = service.loadUserDetails(preAuthToken);

        // Then
        assertThat(userDetails).isInstanceOf(SingleSignOnUser.class)
                .extracting(UserDetails::getUsername).isEqualTo("ssoUser");
    }

    @Test
    public void handlesNullPrincipal() {
        // Given
        PreAuthenticatedAuthenticationToken preAuthToken = new PreAuthenticatedAuthenticationToken(null, null);

        // When
        final UserDetails userDetails = service.loadUserDetails(preAuthToken);

        //Then
        assertThat(userDetails).isNull();
    }
}