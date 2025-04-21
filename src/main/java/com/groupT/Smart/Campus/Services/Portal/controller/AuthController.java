package com.groupT.Smart.Campus.Services.Portal.controller;

import com.groupT.Smart.Campus.Services.Portal.dtos.request.AuthRequest;
import com.groupT.Smart.Campus.Services.Portal.dtos.response.AuthResponse;
import com.groupT.Smart.Campus.Services.Portal.dtos.response.ResponseObject;
import com.groupT.Smart.Campus.Services.Portal.entity.User;
import com.groupT.Smart.Campus.Services.Portal.service.Implementation.UserDetailsServiceImpl;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.AuthService;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> userLogin(@RequestBody AuthRequest authRequest){

        AuthResponse authResponse = this.authService.login(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerUser(@RequestBody User user){

     String response = this.authService.registerUser(user);
     return ResponseEntity.ok(new ResponseObject(response));
    }
}
