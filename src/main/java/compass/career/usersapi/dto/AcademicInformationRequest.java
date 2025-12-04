package compass.career.usersapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AcademicInformationRequest {
    @NotBlank
    @Size(max = 200)
    private String institution;

    @Size(max = 200)
    private String career;

    @DecimalMin("0.00")
    @DecimalMax("100.00")
    private BigDecimal average;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;
    private Boolean inProgress;

    public void validateDateLogic() {
        if (endDate != null && (inProgress == null || inProgress)) {
            inProgress = false;
        }
        if (endDate == null && (inProgress == null || !inProgress)) {
            inProgress = true;
        }
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        LocalDate today = LocalDate.now();
        if (startDate != null && startDate.isAfter(today)) {
            throw new IllegalArgumentException("Start date cannot be in the future");
        }
    }
}