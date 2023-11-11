package com.michalenok.wallet.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Objects;

@Component
public class ApiGatewayHeaderValidatorFilter implements Filter {
    public static final String REQUESTS_FROM_API_GATEWAY = "true";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (Objects.equals(req.getHeader("Is-Proxy-Request"), REQUESTS_FROM_API_GATEWAY)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setStatus(407);
        }
    }
}
