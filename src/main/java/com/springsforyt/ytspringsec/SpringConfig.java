package com.springsforyt.ytspringsec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.springsforyt.ytspringsec.model.MyUserDetailService;

@Configuration
public class SpringConfig {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return myUserDetailService;
    }

    


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
    

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    //     return security
    //             .csrf(csrf->csrf.disable())
    //             .authorizeHttpRequests(auth -> auth
    //                     .requestMatchers("/home","/register/**").permitAll()
    //                     .requestMatchers("/admin/**").hasRole("ADMIN")
    //                     .requestMatchers("/user/**").hasRole("USER")
    //                     )
    //             .formLogin(form -> form.permitAll().defaultSuccessUrl("/user/home"))
    //             .build();
    //             // .formLogin(form -> form.permitAll().defaultSuccessUrl("/admin/home"))
    //             // .build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home", "/register/**", "/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Ensure this page exists
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().iterator().next().getAuthority();
    
                            if ("ROLE_ADMIN".equals(role)) {
                                response.sendRedirect("/admin/home");
                            } else if ("ROLE_USER".equals(role)) {
                                response.sendRedirect("/user/home");
                            } else {
                                response.sendRedirect("/home"); // Default fallback
                            }
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll()
                )
                .build();
    }


}
