package compass.career.usersapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateSkillRequest {
    @NotBlank(message = "Skill name is required")
    @Size(max = 100, message = "Skill name cannot exceed 100 characters")
    private String skillName;

    @NotNull(message = "Proficiency level is required")
    @Min(value = 1, message = "Proficiency level must be at least 1")
    @Max(value = 5, message = "Proficiency level cannot exceed 5")
    private Integer proficiencyLevel;
}