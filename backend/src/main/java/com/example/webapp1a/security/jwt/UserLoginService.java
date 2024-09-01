package com.example.webapp1a.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest, String encryptedAccessToken, String encryptedRefreshToken){
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = SecurityCipher.decrypt(encryptedAccessToken);
        String refreshToken = SecurityCipher.decrypt(encryptedRefreshToken);

        String username = loginRequest.getUsername();
        UserDetails user = userDetailsService.loadUserByUsername(username);

        Boolean accessTokenValid = jwtTokenProvider.validateToken(accessToken);
        Boolean refreshTokenValid = jwtTokenProvider.validateToken(refreshToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken;
        Token newRefreshToken;

        if(!accessTokenValid && !refreshTokenValid){
            newAccessToken = jwtTokenProvider.generateToken(user);
            newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        if(!accessTokenValid && refreshTokenValid){
            newAccessToken = jwtTokenProvider.generateToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);
        }

        if(accessTokenValid && refreshTokenValid){
            newAccessToken = jwtTokenProvider.generateToken(user);
            newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        AuthResponse loginResponse = new AuthResponse(AuthResponse.Status.SUCCESS,
                        "Auth successful. Tokens are created in cookie.");
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }
    
    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token){
        httpHeaders.add(HttpHeaders.SET_COOKIE,
                cookieUtil.createAccessTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }

    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token){
        httpHeaders.add(HttpHeaders.SET_COOKIE,
                cookieUtil.createRefreshTokenRefresh(token.getTokenValue(), token.getDuration()).toString());
    }
}
