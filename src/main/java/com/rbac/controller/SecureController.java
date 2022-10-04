package com.rbac.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    @GetMapping("/getSecure")
    @PreAuthorize("hasAuthority('admin')")
    public String getSecure() {
        System.out.println("getSecure");
        return "getSecure";
    }

    @GetMapping("/getSecureUser")
    @PreAuthorize("hasAuthority('user')")
    public String getSecureUser() {
        System.out.println("getSecureUser");
        return "getSecureUser";
    }
}
