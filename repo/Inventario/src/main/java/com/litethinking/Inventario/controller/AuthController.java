package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.AuthRequest;
import com.litethinking.Inventario.dto.AuthResponse;
import com.litethinking.Inventario.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse createToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // Autenticar usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new Exception("Invalid username or password");
        }

        // Obtener los detalles del usuario
        final UserDetails userDetails = (UserDetails) authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
                )
                .getPrincipal();

        // Generar el token
        final String jwtToken = jwtUtil.generateToken(userDetails);

        // Obtener el rol del usuario
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("ROLE_USER");

        // Devolver el token y los detalles del usuario
        return new AuthResponse(jwtToken, userDetails.getUsername(), role);
    }
}
