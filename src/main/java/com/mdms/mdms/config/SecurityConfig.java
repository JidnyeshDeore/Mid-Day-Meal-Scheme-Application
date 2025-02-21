package com.mdms.mdms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mdms.mdms.service.serviceImpl.SecurityCustomUserDetailsServiceImpl;
import com.mdms.mdms.service.serviceImpl.SupplierUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private SecurityCustomUserDetailsServiceImpl customUserDetailsServiceImpl; // for School

    @Autowired
    private SupplierUserDetailsServiceImpl supplierUserDetailsServiceImpl;  // for Supplier

    @Autowired
    private OAuthAuthenticationSuccessHandler oAuthHandler;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .authenticationProvider(authenticationProvider())
            .authenticationProvider(supplierAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(customUserDetailsServiceImpl);

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public DaoAuthenticationProvider supplierAuthenticationProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(supplierUserDetailsServiceImpl);

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }




    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //     httpSecurity
    //         .authorizeHttpRequests(authorize -> {
    //             authorize
    //                 .requestMatchers("/school/**").authenticated()
    //                 .requestMatchers("/supplier/**").authenticated()
    //                 .anyRequest().permitAll();
    //         })
    //         .formLogin(schoolFormLogin -> {
    //             schoolFormLogin
    //                 .loginPage("/school-login")
    //                 .loginProcessingUrl("/school-authenticate")
    //                 .successForwardUrl("/school/dashboard")
    //                 .usernameParameter("Email")
    //                 .passwordParameter("Password");
    //         })
    //         // .formLogin(supplierFormLogin -> {
    //         //     supplierFormLogin
    //         //         .loginPage("/supplier-login")
    //         //         .loginProcessingUrl("/supplier-authenticate")
    //         //         .successForwardUrl("/supplier/dashboard")
    //         //         .usernameParameter("supplierEmail")
    //         //         .passwordParameter("supplierPassword");
    //         // })
    //         .csrf(AbstractHttpConfigurer::disable)
    //         .logout(schoolLogout -> {
    //             schoolLogout
    //                 .logoutUrl("/school-logout")
    //                 .logoutSuccessUrl("/school-login?school-logout=true");
    //         })
    //         .logout(supplierLogout -> {
    //             supplierLogout
    //                 .logoutUrl("/supplier-logout")
    //                 .logoutSuccessUrl("/supplier-login?supplier-logout=true");
    //         })
    //         .oauth2Login(oauth -> {
    //             oauth
    //                 .loginPage("/login")
    //                 .successHandler(oAuthHandler);
    //         });

    //     return httpSecurity.build();
    // }
    


    @Bean
    @Order(1)
    public SecurityFilterChain schoolSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(authorize -> {
                authorize
                    .requestMatchers("/school/**").authenticated()
                    .anyRequest().permitAll();
            })
            .formLogin(schoolFormLogin -> {
                schoolFormLogin
                    .loginPage("/school-login")
                    .loginProcessingUrl("/school-authenticate")
                    .successForwardUrl("/school/dashboard")
                    .usernameParameter("Email")
                    .passwordParameter("Password");
            })
            .csrf(AbstractHttpConfigurer::disable)
            .logout(schoolLogout -> {
                schoolLogout
                    .logoutUrl("/school-logout")
                    .logoutSuccessUrl("/school-login?school-logout=true");
            })
            .oauth2Login(oauth -> {
                oauth
                    .loginPage("/login")
                    .successHandler(oAuthHandler);
            });

        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain supplierSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(authorize -> {
                authorize
                    .requestMatchers("/supplier/**").authenticated()
                    .anyRequest().permitAll();
            })
            .formLogin(supplierFormLogin -> {
                supplierFormLogin
                    .loginPage("/supplier-login")
                    .loginProcessingUrl("/supplier-authenticate")
                    .successHandler(new SupplierAuthenticationSuccessHandler())
                    .usernameParameter("supplierEmail")
                    .passwordParameter("supplierPassword");
            })
            .csrf(AbstractHttpConfigurer::disable)
            .logout(supplierLogout -> {
                supplierLogout
                    .logoutUrl("/supplier-logout")
                    .logoutSuccessUrl("/supplier-login?supplier-logout=true");
            });

        return httpSecurity.build();
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity security)throws  Exception{

        security
            .authorizeHttpRequests(auth->{
                auth
                .requestMatchers("/admin/**").authenticated()
                .anyRequest().permitAll();
        });
        return  security.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
