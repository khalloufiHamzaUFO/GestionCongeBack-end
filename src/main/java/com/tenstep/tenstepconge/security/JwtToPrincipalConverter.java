package com.tenstep.tenstepconge.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tenstep.tenstepconge.Services.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {
    private UserService userService;

    public UserPrincipal convert(DecodedJWT jwt){
        return UserPrincipal.builder()
                .userId(jwt.getSubject())
                .email(jwt.getClaim("email").asString())
                .authorities(extractAuthoritiesFromClaim(jwt))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
        var claim = jwt.getClaim("role");
        if(claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
