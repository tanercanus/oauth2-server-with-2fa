package com.tanerus.security.authserver.config;

import com.tanerus.security.authserver.service.CustomDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	CustomDetailsService customDetailsService;

	@Override
	public void configure(HttpSecurity http) throws Exception { // @formatter:off


		http.authorizeRequests()
				.antMatchers("/login", "/oauth/authorize", "/secure/two_factor_authentication","/exit", "/resources/**")
				.permitAll();

		//http.authorizeRequests().antMatchers("/resources/**", "/css/**").permitAll().anyRequest().permitAll();


        /*http.antMatcher("/admin**")
                .authorizeRequests().anyRequest().hasRole("ADMIN");*/









		/*http.authorizeRequests().antMatchers(
				"----/ResourceServerConfig configure(HttpSecurity http) ----",
				"/login", "/oauth/authorize", "/secure/two_factor_authentication","/exit", "/resources/**",
				"/login","/login**",
				"/oauth/token", "/oauth/authorize**").permitAll();*/

		/*http.antMatcher("/principal**")
				.authorizeRequests().anyRequest().hasRole("REAL_ADMIN")
				.and()
				.formLogin().loginPage("/login")
				.permitAll();*/

		/*http.requestMatchers().antMatchers("/principal")
				.and().authorizeRequests()
				.antMatchers("/principal").access("hasRole('REAL_ADMIN')");*/

		/*http.authorizeRequests().antMatchers("/principal").access("hasRole('REAL_ADMIN')");*/

		//http.authorizeRequests().anyRequest().access("hasRole('ROLE_SYSTEMADMIN')");

		/*http.authorizeRequests().antMatchers("/principaltanerrrrr","/principal").access("hasRole('REAL_ADMIN')")
                .and()
                .authorizeRequests().anyRequest().access("hasRole('ROLE_SYSTEMADMIN')");*/

		//http.antMatcher("/sss").authorizeRequests().anyRequest().authenticated();


		/*http.authorizeRequests()
				.antMatchers(

						"----/ResourceServerConfig configure(HttpSecurity http) ----",
						"/login", "/oauth/authorize", "/secure/two_factor_authentication","/exit", "/resources/**")
				.permitAll()
				.and()
				.authorizeRequests()
				.anyRequest()
				.authenticated();*/


	} // @formatter:on

}
