package com.hexaware.jumbo.config;

import com.hexaware.jumbo.security.JwtAuthenticationEntryPoint;
import com.hexaware.jumbo.security.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebsecurityConfig.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * JwtAuthenticationEntryPoint.
     */
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    /**
     * UserDetailsService.
     */
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    /**
     * jwtFilter.
     */
    @Autowired
    private JwtFilter jwtFilter;

    /**
     * @param auth auth.
     * @throws Exception exception.
     */
    @Autowired
    public final void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * @return BCryptPassword.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * configure the request.
     */
    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                // this method excludes urls given in matcher
                .authorizeRequests().antMatchers("/jumbo/api/v1/register", "/jumbo/api/v1/login", "/h2-console/**")
                .permitAll()
                // and all others urls should be authenticted before giving response
                .anyRequest().authenticated().and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // add a filter to validate the token for every request
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().frameOptions().disable();
    }

    /**
     * configure WebSecurity.
     */
    @Override
    public void configure(final WebSecurity web) throws Exception {
        //Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")
            .antMatchers("/swagger-resources/**")
            .antMatchers("/swagger-ui.html")
            .antMatchers("/configuration/**")
            .antMatchers("/webjars/**")
            .antMatchers("/public");

    }
}
