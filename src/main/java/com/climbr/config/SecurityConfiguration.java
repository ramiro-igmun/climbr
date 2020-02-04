package com.climbr.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsService climbRatUserDetailsService;

  public SecurityConfiguration(UserDetailsService climbRatUserDetailsService) {
    this.climbRatUserDetailsService = climbRatUserDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.cors().disable();
    http.headers().frameOptions().sameOrigin();
    http.authorizeRequests()
            .antMatchers("/home/**","/*/wallpost/**","/wallpost/**").authenticated().and()
            .formLogin().permitAll().and()
            .logout().permitAll();
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(climbRatUserDetailsService);
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

}

