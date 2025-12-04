package compass.career.usersapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkExperienceRequest {
    @NotBlank
    @Size(max = 200)
    private String company;

    @NotBlank
    @Size(max = 150)
    private String position;

    @NotBlank
    @Size(min = 50)
    private String description;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;
    private Boolean currentJob;

    public void validateDateLogic() {
        if (endDate != null && (currentJob == null || currentJob)) {
            currentJob = false;
        }
        if (endDate == null && (currentJob == null || !currentJob)) {
            currentJob = true;
        }
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        LocalDate today = LocalDate.now();
        if (startDate != null && startDate.isAfter(today)) {
            throw new IllegalArgumentException("Start date cannot be in the future");
        }
        if (endDate != null && endDate.isAfter(today)) {
            throw new IllegalArgumentException("End date cannot be in the future");
        }
    }
}