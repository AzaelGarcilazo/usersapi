package compass.career.usersapi.service;

import compass.career.usersapi.dto.CompleteProfileResponse;

public interface ProfileService {
    CompleteProfileResponse getCompleteProfile(Integer userId);
}