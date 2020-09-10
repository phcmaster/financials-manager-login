package br.com.fm.login.service;


import br.com.fm.login.dto.TokenResponse;
import br.com.fm.login.dto.UserRequest;
import br.com.fm.mongodb.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.expiration}")
    private String jwtExpiration;

    @Value("${jwt.secret}")
    private String jwtSecret;


    public ResponseEntity<TokenResponse> authenticationUser(UserRequest userRequest) throws AuthenticationException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = requestToAuth(userRequest);

        try {
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            String token = generateToken(authenticate);

            return ResponseEntity.ok().body(new TokenResponse(token, "Bearer"));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }


    }


    private UsernamePasswordAuthenticationToken requestToAuth(UserRequest userRequest) {
        return new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword());
    }


    public String generateToken(Authentication authentication) {

        UserEntity loggedUser = (UserEntity) authentication.getPrincipal();

        return Jwts.builder()
                .setIssuer("Fm login")
                .setSubject(loggedUser.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpiration)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }


    public boolean isTokenValid(String token) {

        try {
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public String tokenGetUserId(String token) {
        Claims body = Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody();
        return body.getSubject();
    }
}
