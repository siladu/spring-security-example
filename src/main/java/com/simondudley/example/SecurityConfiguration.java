package com.simondudley.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Configuration
    public static class EndpointSecurity extends WebSecurityConfigurerAdapter {

        private final UserDetailsService typicalUserDetailsService;

        public EndpointSecurity(UserDetailsService typicalUserDetailsService) {
            this.typicalUserDetailsService = typicalUserDetailsService;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .addFilter(createSsoFilter())
                .authorizeRequests()
                .antMatchers("/unprotected").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        }

        private RequestHeaderAuthenticationFilter createSsoFilter() throws Exception {
            final RequestHeaderAuthenticationFilter ssoFilter = new RequestHeaderAuthenticationFilter();
            // We use the presence of x-auth-user for SSO authentication
            // Our platform doesn't allow the manual setting of this header from the outside
            ssoFilter.setPrincipalRequestHeader("x-auth-user");
            ssoFilter.setExceptionIfHeaderMissing(false); // allow basic authentication if no header present
            ssoFilter.setAuthenticationManager(authenticationManager());
            return ssoFilter;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
            preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new SingleSignOnUserDetailsService());
            auth.authenticationProvider(preAuthenticatedAuthenticationProvider);

            auth.userDetailsService(typicalUserDetailsService);
        }
    }
}
