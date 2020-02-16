package com.example.demo;

import com.microsoft.azure.spring.autoconfigure.aad.AADAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// @EnableWebSecurity
public class HttpSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private AADAuthenticationFilter aadAuthFilter;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http
        //     .antMatcher("/**")
        //     .authorizeRequests()
        //     .anyRequest().authenticated()
        // .and()
        //     .o
            
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/api/**").authenticated();

        http.addFilterBefore(aadAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
