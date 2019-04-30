package com.tanerus.security.authserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PrincipalController {


    @GetMapping("admin/principal")
    public Principal userAdmin(Principal principal) {

        return principal;
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("principal")
    public Principal userOld(Principal principal) {

        return principal;
    }

    @GetMapping("/principal1")
    public Principal user(Principal principal) {

        return principal;
    }

}
