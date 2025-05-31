package sv.edu.udb.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sv.edu.udb.controller.request.UserRequest;
import sv.edu.udb.controller.response.UserResponse;
import sv.edu.udb.repository.UserRepository;
import sv.edu.udb.repository.domain.SecurityUser;
import sv.edu.udb.service.SecUserService;
import sv.edu.udb.service.mapper.UserMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SecUserServiceImpl implements SecUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(final UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        // Usa el rol enviado en el request, o por defecto "USER"
        String assignedRole = (userRequest.getRole() != null && !userRequest.getRole().isBlank())
                ? userRequest.getRole().toUpperCase()
                : "USER";

        final SecurityUser user = SecurityUser.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(assignedRole)
                .fechaCreacion(LocalDateTime.now())
                .build();

        return userMapper.toUserResponse(userRepository.save(user));
    }
}
