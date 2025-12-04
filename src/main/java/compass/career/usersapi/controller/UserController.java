package compass.career.usersapi.controller;

import compass.career.usersapi.dto.SkillsDTO;
import compass.career.usersapi.dto.UserBasicInfoDTO;
import compass.career.usersapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@Tag(name = "Users - Internal", description = "Internal endpoints for inter-microservice communication")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/basic-info")
    @Operation(
            summary = "Get user basic information (Internal)",
            description = "Returns basic user information for inter-microservice communication."
    )
    public ResponseEntity<UserBasicInfoDTO> getUserBasicInfo(@PathVariable Integer userId) {
        UserBasicInfoDTO userInfo = userService.getUserBasicInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/{userId}/skills")
    @Operation(
            summary = "Get user skills (Internal)",
            description = "Returns all user skills for inter-microservice communication."
    )
    public ResponseEntity<SkillsDTO> getUserSkills(@PathVariable Integer userId) {
        SkillsDTO skills = userService.getUserSkills(userId);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{userId}/exists")
    @Operation(
            summary = "Check if user exists (Internal)",
            description = "Validates if a user exists in the system."
    )
    public ResponseEntity<Boolean> userExists(@PathVariable Integer userId) {
        boolean exists = userService.userExists(userId);
        return ResponseEntity.ok(exists);
    }
}