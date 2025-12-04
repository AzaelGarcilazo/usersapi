package compass.career.usersapi.service;

import compass.career.usersapi.dto.SkillsDTO;
import compass.career.usersapi.dto.UserBasicInfoDTO;

public interface UserService {
    UserBasicInfoDTO getUserBasicInfo(Integer userId);
    SkillsDTO getUserSkills(Integer userId);
    boolean userExists(Integer userId);
}