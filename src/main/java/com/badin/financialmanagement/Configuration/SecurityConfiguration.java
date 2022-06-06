package com.badin.financialmanagement.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // just for demonstration only, shouldn't hardcode password
        auth.inMemoryAuthentication()
                .withUser("dummy")
                .password("{noop}password")
                .authorities("USER_ROLE")
                .and()
                .withUser("user")
                .password("{noop}userpass")
                .authorities("USER_ROLE");

        // Database authentication
//
//        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//            .dataSource(dataSource)
//            .usersByUsernameQuery("select username, password, enabled from users where username=?")
//            .authoritiesByUsernameQuery("select username, role from users where username=?");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**/*.js", "/**/*.css").permitAll()
                .anyRequest().authenticated()

                .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login-error")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/index")
                    .permitAll();
    }

}