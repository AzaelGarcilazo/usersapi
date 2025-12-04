package compass.career.usersapi.service;

import compass.career.usersapi.dto.SkillRequest;
import compass.career.usersapi.dto.SkillResponse;
import compass.career.usersapi.dto.UpdateSkillRequest;

import java.util.List;

public interface SkillService {
    SkillResponse create(Integer userId, SkillRequest request);
    SkillResponse update(Integer userId, Integer id, UpdateSkillRequest request);
    List<SkillResponse> findByUserId(Integer userId, int page, int pageSize);
}