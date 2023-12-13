package com.michalenok.wallet.security;

import io.github.resilience4j.core.lang.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();
    private static String PREFERRED_USERNAME = "preferred_username";
    private static String RESOURCE_ACCESS = "realm_access";
    private static String ROLES = "roles";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }

    public String getPrincipleClaimName(Jwt jwt) {
        if (jwt.getClaim(PREFERRED_USERNAME) == null) {
            return null;
        }
        return jwt.getClaim(PREFERRED_USERNAME);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Collection<String> resourceRoles;

        if (jwt.getClaim(RESOURCE_ACCESS) == null) {
            return List.of();
        }
        resourceAccess = jwt.getClaim(RESOURCE_ACCESS);
        resourceRoles = (Collection<String>) resourceAccess.get(ROLES);

        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
