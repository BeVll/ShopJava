package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.configuration.security.JwtService;
import org.example.dto.account.AuthResponseDto;
import org.example.dto.account.LoginDto;
import org.example.dto.account.RegisterDto;
import org.example.entities.UserEntity;
import org.example.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDto login(LoginDto dto) {
        var userAuth = userRepository.findByEmail(dto.getEmail());
        if(!userAuth.isPresent())
            throw new UsernameNotFoundException("Користувача не знайдено");
        var user = userAuth.get();
        if(user.isGoogleAuth())
            throw new AccountException("Ваш акаунт війшов через гугл");
        var isValid = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if(!isValid)
            throw new UsernameNotFoundException("Користувача не знайдено");

        var jwtToken = jwtService.generateAccessToken(user);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponseDto register(RegisterDto dto) {
        var userAuth = userRepository.findByEmail(dto.getEmail());
        if(userAuth.isPresent())
            throw new UsernameNotFoundException("User is exist");

        var user = UserEntity
                .builder()
                .firstName(dto.getFirstname())
                .lastName(dto.getLastname())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isGoogleAuth(false)
                .build();
        userRepository.save(user);

        var userCreated = userAuth.get();


        var jwtToken = jwtService.generateAccessToken(userCreated);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponseDto getUserToken(UserEntity user) {

        var jwtToken = jwtService.generateAccessToken(user);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}
