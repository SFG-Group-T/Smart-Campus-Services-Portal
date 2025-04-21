package com.groupT.Smart.Campus.Services.Portal.service.Interface;

import com.groupT.Smart.Campus.Services.Portal.dtos.request.AuthRequest;
import com.groupT.Smart.Campus.Services.Portal.dtos.response.AuthResponse;
import com.groupT.Smart.Campus.Services.Portal.entity.User;

public interface AuthService {

    public String registerUser(User user);
    public AuthResponse login (AuthRequest authRequest);
}
