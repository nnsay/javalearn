package com.neuralgalaxy.commons.visitor.config;

import com.neuralgalaxy.commons.visitor.jwt.filter.JwtTokenFilter;
import com.neuralgalaxy.commons.visitor.handler.DeniedHandler;
import com.neuralgalaxy.commons.visitor.handler.UnauthorizedHandler;
import com.neuralgalaxy.commons.web.resolver.OverallExceptionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220228
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class VisitorWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenFilter tokenFilter;

    @Autowired
    OverallExceptionResolver resolver;

    @Autowired(required = false)
    List<WebSecurityCustomizer> webSecurityCustomizers = new ArrayList<>();

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new DeniedHandler(this.resolver);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new UnauthorizedHandler(this.resolver);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        webSecurityCustomizers.forEach(wsc -> wsc.customizer(web));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http.csrf().disable()
                .httpBasic().disable()
                //.authenticationProvider()
                .authorizeRequests().anyRequest().permitAll()
                .and().exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
                    .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        //@formatter:on
        webSecurityCustomizers.forEach(wsc -> wsc.customizer(http));
    }
}
