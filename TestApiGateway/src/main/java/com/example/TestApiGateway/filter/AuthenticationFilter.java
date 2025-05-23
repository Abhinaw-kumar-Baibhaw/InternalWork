package com.example.TestApiGateway.filter;

import com.example.TestApiGateway.config.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.logging.Logger;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.warning("Missing Authorization header in request: " + exchange.getRequest().getURI());
                    return Mono.error(new RuntimeException("Missing Authorization header"));
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                } else {
                    logger.warning("Authorization header does not start with 'Bearer '");
                    return Mono.error(new RuntimeException("Authorization header must start with 'Bearer '"));
                }
                try {
                    jwtUtil.validateToken(authHeader);
                    String s = jwtUtil.extractRoles(authHeader);
                    String path = exchange.getRequest().getURI().getPath();
                    if (path.contains("/products/create") || path.contains("/products/update") || path.contains("/products/delete/{productId}")) {
                        if (s != null && s.contains("admin")) {
                            return chain.filter(exchange);
                        } else {
                            logger.warning("Access denied for user without admin role.");
                            return Mono.error(new RuntimeException("Access Denied: Admin role required"));
                        }
                    }else {
                        return chain.filter(exchange);
                    }
                } catch (Exception e) {
                    logger.severe("Invalid Token: " + e.getMessage());
                    return Mono.error(new RuntimeException("Invalid Token"));
                }
            }
            return chain.filter(exchange);
        };
    }
    public static class Config {
    }
}
