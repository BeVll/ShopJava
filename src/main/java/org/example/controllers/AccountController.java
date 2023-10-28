package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.account.AuthResponseDto;
import org.example.dto.account.GoogleAuthDto;
import org.example.dto.account.LoginDto;
import org.example.dto.account.RegisterDto;
import org.example.entities.UserEntity;
import org.example.repositories.UserRepository;
import org.example.services.AccountException;
import org.example.services.AccountService;
import org.example.services.GoogleApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;
    private final GoogleApiService googleApiService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginDto dto) {
        try {
            var auth = service.login(dto);
            return ResponseEntity.ok(auth);
        }
        catch (AccountException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterDto dto) {
        try {
            var auth = service.register(dto);
            return ResponseEntity.ok(auth);
        }
        catch (AccountException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("google")
    public ResponseEntity<AuthResponseDto> googleLogin(@RequestBody GoogleAuthDto dto) {
        try {
            var userInfo = googleApiService.getUserInfo(dto.getAccess_token());
            var userAuth = userRepository.findByEmail(userInfo.getEmail());
            UserEntity user = null;
            if(!userAuth.isPresent()) {
                user = UserEntity
                        .builder()
                        .firstName(userInfo.getGiven_name())
                        .lastName(userInfo.getGiven_name())
                        .email(userInfo.getEmail())
                        .phone("00 00 000 00 00")
                        .password(passwordEncoder.encode("123456"))
                        .isGoogleAuth(true)
                        .build();
                userRepository.save(user);
            }
            else
                user=userAuth.get();
            var auth = service.getUserToken(user);
            return ResponseEntity.ok(auth);
        }catch(Exception ex) {
            System.out.println("---Error----"+ ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
