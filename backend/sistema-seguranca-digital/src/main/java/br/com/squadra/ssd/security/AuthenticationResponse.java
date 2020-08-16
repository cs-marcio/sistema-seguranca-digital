package br.com.squadra.ssd.security;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter
public class AuthenticationResponse implements Serializable {

    private final String token;
    private Long id;
    private String username;
    private List<String> roles;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
