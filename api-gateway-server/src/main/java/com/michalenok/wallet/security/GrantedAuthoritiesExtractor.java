package com.michalenok.wallet.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
class GrantedAuthoritiesExtractor
        implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;
    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;
    private static String RESOURCE_ACCESS = "resource_access";
    private static String WALLET = "wallet";
    private static String ROLES = "roles";

    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if (jwt.getClaim(RESOURCE_ACCESS) == null) {
            return List.of();
        }
        resourceAccess = jwt.getClaim(RESOURCE_ACCESS);
        if (resourceAccess.get(WALLET) == null) {
            return List.of();
        }
        resource = (Map<String, Object>) resourceAccess.get(WALLET);
        resourceRoles = (Collection<String>) resource.get(ROLES);
        return resourceRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}