package com.test.keycloak.config;

import com.test.keycloak.WebAccessDeniedHandler;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;

import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;



@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class KeySecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    private final WebAccessDeniedHandler webAccessDeniedHandler;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Autowired
    public KeySecurityConfig(WebAccessDeniedHandler webAccessDeniedHandler){
        this.webAccessDeniedHandler = webAccessDeniedHandler;
    }
    // Keycloak applicatoin.properties 형식 설정파일 지원
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new NullAuthenticatedSessionStrategy();
        return new RegisterSessionAuthenticationStrategy(sessionRegistry());        //AuthSession
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        //Keycloak에서 제공해주는 Provider 적용가능
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**")       //이 경로는 검증 무시
                .antMatchers("/css/**")
                .antMatchers("/vendor/**")
                .antMatchers("/js/**")
                .antMatchers("/favicon*/**")
                .antMatchers("/img/**")
                .antMatchers("/image/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http
                .authorizeRequests()
                .antMatchers("/user_manage").access("hasRole('ADMIN')")     //user_manage 경로는 ADMIN 권한만 접근가능
                .anyRequest().authenticated()
                .and()

                // "/" 주소를 permitAll(false) ==>  authenticated() 로 변경해야함.
                .logout()
                .logoutUrl("/logout")       //logout url설정
                .logoutSuccessUrl("/")      //logout 시 루트로 이동
                .and()
                .csrf().disable()
        ;
    }
}
