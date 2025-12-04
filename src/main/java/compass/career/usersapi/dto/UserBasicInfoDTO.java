package compass.career.usersapi.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserBasicInfoDTO {
    Integer userId;
    String name;
    String email;
    String role;
}