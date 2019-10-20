/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.EmployeeApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private AuthenticationEntryPoint authEntryPoint;
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO: .sessionManagement().sessionFixation().newSession().and()
        http
                // Use AuthenticationEntryPoint to authenticate user/password
                .httpBasic().authenticationEntryPoint(authEntryPoint).and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/users/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                // All requests send to the Web Server request must be authenticated
                .anyRequest().authenticated().and().
                logout().logoutSuccessUrl("/users").invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
         
        String password = "12345";
 
        String encrytedPassword = this.passwordEncoder().encode(password);
        System.out.println("Encoded password of 12345=" + encrytedPassword);

        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder>
        mngConfig = auth.inMemoryAuthentication();
 
        // Defines 2 users, stored in memory.
        // ** Spring BOOT >= 2.x (Spring Security 5.x)
        // Spring auto add ROLE_
        UserDetails u1 = User.withUsername("Alexander").password(encrytedPassword).roles("ADMIN").build();
        UserDetails u2 = User.withUsername("User").password(encrytedPassword).roles("USER").build();
 
        mngConfig.withUser(u1).withUser(u2);
 
        // If Spring BOOT < 2.x (Spring Security 4.x)):
        // Spring auto add ROLE_
        // mngConfig.withUser("tom").password("123").roles("USER");
        // mngConfig.withUser("jerry").password("123").roles("USER");
    }
 
}
