package br.com.squadra.ssd.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @Data
public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;
}
