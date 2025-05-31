package sv.edu.udb.configuration.security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sv.edu.udb.repository.UserRepository;
import sv.edu.udb.repository.domain.SecurityUser;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        // Si el usuario no existe, lo creamos con rol USER
        SecurityUser user = userRepository.findByEmail(email).orElseGet(() -> {
            SecurityUser newUser = SecurityUser.builder()
                    .email(email)
                    .username(name != null ? name : email)
                    .password("") // no se usa en OAuth
                    .role("USER")
                    .fechaCreacion(LocalDateTime.now())
                    .build();
            return userRepository.save(newUser);
        });

        // Generamos el JWT
        String token = jwtUtil.generateToken(email);

        // Devolvemos el token como respuesta (puedes redirigir si quieres)
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().flush();
    }
}