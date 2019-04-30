package com.rr.security.authserver.config;

import com.rr.security.authserver.service.CustomDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(-10)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomDetailsService customDetailsService;

	@Autowired
	private UserAuthenticationProvider authProvider;


	/*@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}*/

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	
	@Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Override
	  public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("----/WebSecurityConfiguration configure(WebSecurity web) ----");
	    web.ignoring().antMatchers("/webjars/**");
	    web.ignoring().antMatchers("/static/css/**","/css/**","/fonts/**","/libs/**");
	  }
	  
	  @Override
	  protected void configure(HttpSecurity http) throws Exception { // @formatter:off
	      /* Correct start
	      http.requestMatchers()
	          .antMatchers(
	          		"----/WebSecurityConfiguration configure(HttpSecurity http) ----",
	          		"/login", "/oauth/authorize", "/secure/two_factor_authentication","/exit", "/resources/**")
	          .and()
	          .authorizeRequests()
	          .anyRequest()
	          .authenticated()
	          .and()
	          .formLogin().loginPage("/login")
	          .permitAll();
	          Correct end*/


		  http.requestMatchers()
				  .antMatchers(
						  "----/WebSecurityConfiguration configure(HttpSecurity http) ----",
						  "/login", "/oauth/authorize", "/secure/two_factor_authentication","/exit",/* "/resources/**",*/
						  "/admin/**",
						  "/h2-console/**")
				  .and()

				  .authorizeRequests()
				  //.antMatchers("/admin/**").access("hasRole('ADMIN')")
				  //.antMatchers("/admin/**").hasRole("ADMIN")
				  .antMatchers(HttpMethod.GET, "/principal").access("#oauth2.hasScope('read')")
				  //.hasAuthority("ROLE_ADMIN") --> it works
				  .antMatchers("/h2-console/**").permitAll()

				  .anyRequest()
				  .authenticated()
				  .and()
				  .formLogin().loginPage("/login")
				  .permitAll()
		  .and().csrf().disable();

		  /*http.csrf().disable()
				  .authorizeRequests()
				  .antMatchers("/login", "/secure/two_factor_authentication", "/exit","/resources/**").hasAnyRole()
				  .antMatchers("/oauth/authorize").permitAll()
				  .antMatchers("/admin/**").hasAuthority("REAL_ADMIN")
				  .anyRequest().authenticated()
				  .and()
				  .formLogin().loginPage("/login").permitAll();*/

	  } // @formatter:on


    @Override
    @Autowired // <-- This is crucial otherwise Spring Boot creates its own
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth//.parentAuthenticationManager(authenticationManager)
//                .inMemoryAuthentication()
//                .withUser("demo")
//                .password("demo")
//                .roles("USER");
    	
    	auth.authenticationProvider(authProvider)
				.userDetailsService(customDetailsService).passwordEncoder(passwordEncoder())
		;
    }
}
