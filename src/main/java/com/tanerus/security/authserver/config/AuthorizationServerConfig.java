package com.tanerus.security.authserver.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

import javax.annotation.PostConstruct;

//@Configuration
//@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String secret = "secret";
        //PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedSecret = passwordEncoder.encode(secret);

        clients
                .inMemory()
                .withClient("ClientId")
                .secret(hashedSecret)
                //.secret(passwordEncoder (). encode ("secret"))
                .authorizedGrantTypes(/*"password",*/"authorization_code"/*,"refresh_token"*/)
                .scopes("user_info", "read", "write")
                .authorities(TwoFactorAuthenticationFilter.ROLE_TWO_FACTOR_AUTHENTICATION_ENABLED)
                .accessTokenValiditySeconds(20)
                .refreshTokenValiditySeconds(20)
                //.refreshTokenValiditySeconds(600)
                //.autoApprove(true) --> scope vb seyleri otomatik kabul ediyor.

                .redirectUris("http://localhost:8082/ui/login", "http://localhost:8082/ui/secure", "http://localhost:8081/auth/principal", "/");
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .authenticationManager(authenticationManager)
                .requestFactory(customOAuth2RequestFactory());
    }


    @Bean
    public DefaultOAuth2RequestFactory customOAuth2RequestFactory() {
        return new CustomOAuth2RequestFactory(clientDetailsService);
    }

    @Bean
    public FilterRegistrationBean twoFactorAuthenticationFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(twoFactorAuthenticationFilter());
        registration.addUrlPatterns("/oauth/authorize");
        registration.setName("twoFactorAuthenticationFilter");
        return registration;
    }

    @Bean
    public TwoFactorAuthenticationFilter twoFactorAuthenticationFilter() {
        return new TwoFactorAuthenticationFilter();
    }

    @PostConstruct
    public void init() {
        authorizationEndpoint.setUserApprovalPage("forward:/oauth/taner_confirm_access");
        //authorizationEndpoint.setErrorPage("forward:/oauth/custom_error");
    }

}
