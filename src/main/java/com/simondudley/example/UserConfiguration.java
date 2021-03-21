package com.simondudley.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.List;

@Configuration
public class UserConfiguration {

    @Bean
    public UserDetailsService typicalUserDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        List<UserDetails> users = getUsers(passwordEncoder);
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        users.forEach(manager::createUser);
        return manager;
    }

    private List<UserDetails> getUsers(PasswordEncoder passwordEncoder) {
        return Collections.singletonList(
                User
                    .withUsername("basicUser")
                    .password(passwordEncoder.encode("basicPassword"))
                    .roles("USER")
                    .build()
        );
    }
}
