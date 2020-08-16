package br.com.squadra.ssd.controller;


import br.com.squadra.ssd.security.AuthenticationRequest;
import br.com.squadra.ssd.security.AuthenticationResponse;
import br.com.squadra.ssd.security.CustomUserDetails;
import br.com.squadra.ssd.security.JwtUtils;
import br.com.squadra.ssd.service.JPAUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils utils;

    @Autowired
    private JPAUserDetailService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequest request) throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authenticate = authManager.authenticate(authToken);

        final CustomUserDetails userDetails = (CustomUserDetails) service.loadUserByUsername(request.getUsername());
        final String jwt = utils.generateToken(userDetails);
        AuthenticationResponse response = new AuthenticationResponse(jwt);

        response.setId(userDetails.getId());
        response.setUsername(userDetails.getUsername());
        List<String> roles = new ArrayList<String>();

        userDetails.getAuthorities().forEach((a) -> roles.add(a.getAuthority()));
        response.setRoles(roles);

        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);

    }

    @GetMapping(value = "/password-generate/{password}")
    @ResponseBody
    public String generate(@PathVariable String password) {
        return passwordEncoder.encode(password);
    }

}
