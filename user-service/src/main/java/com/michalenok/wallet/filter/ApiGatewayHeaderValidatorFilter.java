package com.michalenok.wallet.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Objects;

@Component
@Log4j2
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
