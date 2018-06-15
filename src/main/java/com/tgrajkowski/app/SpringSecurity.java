package com.tgrajkowski.app;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    UserDetailsService userDetailsService;

    public SpringSecurity(UserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/auth/signup").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/auth/checkLoginAvailable").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/auth/account/confirm").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/product/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/product/setReminder").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/newsletter/subscribe").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/bucket/coupon").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/category/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/tom/static").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/file/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/newsletter/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/newsletter/confirm").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/location/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/upload/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/upload/post").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/tomek/**").permitAll()


                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest().authenticated()
                .and().addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("authorization", "CREDENTIALS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;


    }
}
