package compass.career.usersapi.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class SkillsDTO {
    Integer userId;
    Map<String, Integer> skills; // skillName -> proficiencyLevel
}