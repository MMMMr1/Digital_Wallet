package com.michalenok.wallet.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
class GrantedAuthoritiesExtractor
        implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static String RESOURCE_ACCESS = "realm_access";
    private static String ROLES = "roles";

    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Collection<String> resourceRoles;

        if (jwt.getClaim(RESOURCE_ACCESS) == null) {
            return List.of();
        }
        resourceAccess = jwt.getClaim(RESOURCE_ACCESS);
        resourceRoles = (Collection<String>) resourceAccess.get(ROLES);

        return resourceRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}