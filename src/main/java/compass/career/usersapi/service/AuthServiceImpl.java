package compass.career.usersapi.service;

import compass.career.usersapi.dto.*;
import compass.career.usersapi.mapper.AuthMapper;
import compass.career.usersapi.model.*;
import compass.career.usersapi.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final RoleRepository roleRepository;
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public LoginResponse register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        // Validar edad mínima
        if (Period.between(request.getBirthDate(), LocalDate.now()).getYears() < 15) {
            throw new IllegalArgumentException("User must be at least 15 years old");
        }

        // Verificar que el email no exista
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        // Verificar que el username no exista
        if (credentialRepository.existsByUsername(request.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        // Buscar el rol
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + request.getRole()));

        log.debug("Creating credentials for username: {}", request.getUsername());

        // Crear credenciales con contraseña encriptada
        Credential credential = new Credential();
        credential.setUsername(request.getUsername());
        credential.setPassword(passwordEncoder.encode(request.getPassword()));
        credential = credentialRepository.save(credential);

        // Crear usuario
        User user = AuthMapper.toEntity(request, credential, role);
        user = userRepository.save(user);

        // ✅ IMPORTANTE: Actualizar la referencia bidireccional
        credential.setUser(user);
        credential = credentialRepository.save(credential);

        log.info("User registered successfully - ID: {}, Email: {}, Role: {}",
                user.getId(), user.getEmail(), role.getName());

        // ✅ Generar token JWT con el claim "role"
        String jwtToken = jwtService.generateToken(credential);

        log.debug("JWT token generated for user: {}", user.getEmail());

        return AuthMapper.toLoginResponse(user, jwtToken);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        // Autenticar con Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Buscar usuario por email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Invalid credentials"));

        // Generar token JWT
        String jwtToken = jwtService.generateToken(user.getCredential());

        return AuthMapper.toLoginResponse(user, jwtToken);
    }

    @Override
    @Transactional
    public void requestPasswordRecovery(PasswordRecoveryRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Email not found"));

        // Generar token para recuperación
        String token = java.util.UUID.randomUUID().toString();
        PasswordRecovery recovery = new PasswordRecovery();
        recovery.setUser(user);
        recovery.setToken(token);
        recovery.setUsed(false);
        passwordRecoveryRepository.save(recovery);

        // TODO: Enviar email con el token de recuperación
    }

    @Override
    @Transactional
    public void changePassword(Integer userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Verificar contraseña antigua usando BCrypt
        if (!passwordEncoder.matches(request.getOldPassword(), user.getCredential().getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Actualizar contraseña con hash
        Credential credential = user.getCredential();
        credential.setPassword(passwordEncoder.encode(request.getNewPassword()));
        credentialRepository.save(credential);
    }

    @Override
    @Transactional
    public User updateProfile(Integer userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Validar edad mínima
        if (Period.between(request.getBirthDate(), LocalDate.now()).getYears() < 15) {
            throw new IllegalArgumentException("User must be at least 15 years old");
        }

        AuthMapper.copyToEntity(request, user);
        return userRepository.save(user);
    }
}