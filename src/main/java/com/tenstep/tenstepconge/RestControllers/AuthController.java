package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.dao.Model.RegisterDto;
import com.tenstep.tenstepconge.errors.InvalidCredentials;
import com.tenstep.tenstepconge.errors.UserNotEnabled;
import com.tenstep.tenstepconge.security.JwtIssuer;
import com.tenstep.tenstepconge.Services.AuthService;
import com.tenstep.tenstepconge.Services.email.EmailService;
import com.tenstep.tenstepconge.dao.Model.LoginRequest;
import com.tenstep.tenstepconge.dao.Model.LoginResponce;
import com.tenstep.tenstepconge.dao.entities.User;
import com.tenstep.tenstepconge.dao.repositories.UserRepository;
import com.tenstep.tenstepconge.errors.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponce> login(@RequestBody @Validated LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            System.out.println("Email and password are required");
            return ResponseEntity.badRequest().build();
        }
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User not found by email");
        }
        if (!user.isEnabled()) {
            throw new UserNotEnabled("User is not authorized");
        }
        try {
            LoginResponce loginResponse = authService.attemtptLogin(request.getEmail(), request.getPassword());
            authService.lastLogin(request.getEmail());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            throw new InvalidCredentials("Invalid credentials: " + e.getMessage());
        }
    }



        @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        User user = userRepo.findByEmail(registerDto.getEmail());
        if (user != null) {
            throw new UserNotFoundException("User exist by that email");
        }
        authService.registerUser(registerDto);
        return ResponseEntity.ok("User registered successfully");
    }



    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String verificationToken) {
        if (validateVerificationToken(verificationToken)) {
            return ResponseEntity.ok("User verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification token");
        }
    }

    private boolean validateVerificationToken(String verificationToken) {
        User u = userRepo.findByVerificationToken(verificationToken);
        if (u.getVerificationToken().equals(verificationToken)) {
            if (u.isEnabled()) {
                return true;
            }else if(!u.isEnabled()){
                u.setEnabled(true);
                userRepo.save(u);
                return true;
            }
        }
        return false;
    }
}
