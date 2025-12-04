package compass.career.usersapi.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SkillResponse {
    Integer id;
    String skillName;
    Integer proficiencyLevel;
}