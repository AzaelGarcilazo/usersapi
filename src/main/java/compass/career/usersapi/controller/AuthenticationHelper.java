package compass.career.usersapi.controller;

import compass.career.usersapi.model.Credential;
import compass.career.usersapi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationHelper {

    public User getAuthenticatedUser(Authentication authentication) {
        Credential credential = (Credential) authentication.getPrincipal();
        return credential.getUser();
    }

    public String getAuthenticatedUserEmail(Authentication authentication) {
        return authentication.getName();
    }
}