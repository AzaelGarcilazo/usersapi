package compass.career.usersapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SkillRequest {
    @NotBlank
    @Size(max = 100)
    private String skillName;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer proficiencyLevel;
}