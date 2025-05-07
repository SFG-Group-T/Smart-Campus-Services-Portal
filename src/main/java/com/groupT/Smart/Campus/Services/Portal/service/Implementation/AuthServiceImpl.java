package com.groupT.Smart.Campus.Services.Portal.service.Implementation;

import com.groupT.Smart.Campus.Services.Portal.dtos.request.AuthRequest;
import com.groupT.Smart.Campus.Services.Portal.dtos.response.AuthResponse;
import com.groupT.Smart.Campus.Services.Portal.entity.Lecturer;
import com.groupT.Smart.Campus.Services.Portal.entity.Student;
import com.groupT.Smart.Campus.Services.Portal.entity.User;
import com.groupT.Smart.Campus.Services.Portal.exception.InvalidLoginException;
import com.groupT.Smart.Campus.Services.Portal.repository.LecturerRepository;
import com.groupT.Smart.Campus.Services.Portal.repository.StudentRepository;
import com.groupT.Smart.Campus.Services.Portal.repository.UserRepository;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.AuthService;
import com.groupT.Smart.Campus.Services.Portal.service.Interface.JwtService;
import com.groupT.Smart.Campus.Services.Portal.util.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    @Transactional
    public String registerUser(User user) {

        if (user instanceof Student student) {

            if (studentRepository.existsByEmail(student.getEmail())) {
                throw new RuntimeException("Student with this email already exists");
            }
            student.setRole(Role.STUDENT);
            student.setPassword(passwordEncoder.encode(student.getPassword()));
            studentRepository.save(student);

            return "Student registered successfully";

        } else if (user instanceof Lecturer lecturer) {

            if (lecturerRepository.existsByEmail(lecturer.getEmail())) {
                throw new RuntimeException("Lecturer with this email already exists");
            }
            lecturer.setRole(Role.LECTURER);
            lecturer.setPassword(passwordEncoder.encode(lecturer.getPassword()));
            lecturerRepository.save(lecturer);

            return "Lecturer registered successfully";
        }

        throw new IllegalArgumentException("Unsupported user type");

    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {

        String jwt;

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getUsername());
            jwt = this.jwtService.generateToken(userDetails);

        } catch (AuthenticationException ex) {
            throw new InvalidLoginException("Invalid credentials", ex);
        }

        return new AuthResponse(jwt);
    }


}
