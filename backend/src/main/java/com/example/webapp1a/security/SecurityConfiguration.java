package com.example.webapp1a.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    
    @Autowired
    UserDetailService userDetailsService;    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf().disable()

            .authorizeHttpRequests(registry -> {
                registry.antMatchers("/items/").hasAnyRole("ADMIN");
                registry.antMatchers("/items/{id}").hasAnyRole("ADMIN");
                registry.antMatchers("/items/{id}/update").hasAnyRole("ADMIN");
                registry.antMatchers("/items/{id}/clothes/stock").hasAnyRole("ADMIN");
                registry.antMatchers("/items/clothes/page").hasAnyRole("ADMIN");
                registry.antMatchers("/items/clothes/new").hasAnyRole("ADMIN");
                registry.antMatchers("/items/{id}/clothes/stock/new").hasAnyRole("ADMIN");
                registry.antMatchers("/items/{id}/shoes/stock").hasAnyRole("ADMIN");
                registry.antMatchers("/items/shoes/page").hasAnyRole("ADMIN");
                registry.antMatchers("/items/shoes/new").hasAnyRole("ADMIN");
                registry.antMatchers("/items/{id}/shoes/stock/new").hasAnyRole("ADMIN");
                registry.antMatchers("/items/{id}/clothes/{index}/delete").hasAnyRole("ADMIN");
                registry.antMatchers("/items/{id}/shoes/{index}/delete").hasAnyRole("ADMIN");
                registry.antMatchers("/items/{id}/delete").hasAnyRole("ADMIN");
            });
            
        http.headers(header -> header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));
            
        return http.build();
    }

    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:8443")
                .allowCredentials(true);
    }
}
