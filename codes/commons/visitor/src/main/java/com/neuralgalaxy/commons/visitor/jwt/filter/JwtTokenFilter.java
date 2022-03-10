package com.neuralgalaxy.commons.visitor.jwt.filter;

import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import com.neuralgalaxy.commons.web.resolver.OverallExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token 获取用户
 *
 * @author haiker
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    VisitorSerializer serializer;
    OverallExceptionResolver resolver;

    public JwtTokenFilter(VisitorSerializer serializer, OverallExceptionResolver resolver) {
        this.serializer = serializer;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 未认证或者一名用户需要重新获取认证
        if( null == authentication || authentication instanceof AnonymousAuthenticationToken) {
            final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(token)) {

                Visitor details = null;
                try {
                    details = serializer.decode(token.substring(7));
                } catch (Exception e) {
                    resolver.resolveException(request, response, serializer, e);
                    return;
                }

                PreAuthenticatedAuthenticationToken jwtAuth = new PreAuthenticatedAuthenticationToken(details.getId(), token);
                jwtAuth.setAuthenticated(true);
                jwtAuth.setDetails(details);
                SecurityContextHolder.getContext().setAuthentication(jwtAuth);
            }
        }

        chain.doFilter(request, response);
    }
}
