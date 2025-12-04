package compass.career.usersapi.service;

import compass.career.usersapi.dto.WorkExperienceRequest;
import compass.career.usersapi.dto.WorkExperienceResponse;

import java.util.List;

public interface WorkExperienceService {
    WorkExperienceResponse create(Integer userId, WorkExperienceRequest request);
    WorkExperienceResponse update(Integer userId, Integer id, WorkExperienceRequest request);
    List<WorkExperienceResponse> findByUserId(Integer userId, int page, int pageSize);
}