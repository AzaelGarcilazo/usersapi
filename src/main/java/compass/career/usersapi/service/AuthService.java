package compass.career.usersapi.service;

import compass.career.usersapi.dto.*;
import compass.career.usersapi.model.User;

public interface AuthService {
    LoginResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void requestPasswordRecovery(PasswordRecoveryRequest request);
    void changePassword(Integer userId, ChangePasswordRequest request);
    User updateProfile(Integer userId, UpdateProfileRequest request);
}