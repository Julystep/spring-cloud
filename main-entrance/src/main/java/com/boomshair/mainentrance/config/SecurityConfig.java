package com.boomshair.mainentrance.config;

import com.boomshair.mainentrance.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity // 该注解默认配置了适合WebFlux的SpringSecurity
public class SecurityConfig  {

    @Bean
    @Validated
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .cors().disable()
                .authorizeExchange().pathMatchers("/login", "/user/register").permitAll()
                .anyExchange().authenticated()
                .and().httpBasic().and().formLogin()
                .and().build();
    }

    @Bean
    @Validated
    public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            com.boomshair.mainentrance.entity.User user = userRepository.getUserByUsername(username);
            System.out.println(encoder.encode(user.getPassword()));
            UserDetails userDetails = User.withUsername(user.getUsername()).password(encoder.encode(user.getPassword())).roles("USER").build();
            return Mono.just(userDetails);
        };
    }

}
