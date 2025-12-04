package compass.career.usersapi.service;

import compass.career.usersapi.dto.SkillsDTO;
import compass.career.usersapi.dto.UserBasicInfoDTO;
import compass.career.usersapi.model.Skill;
import compass.career.usersapi.model.User;
import compass.career.usersapi.repository.SkillRepository;
import compass.career.usersapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    @Override
    @Transactional(readOnly = true)
    public UserBasicInfoDTO getUserBasicInfo(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return UserBasicInfoDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public SkillsDTO getUserSkills(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }

        List<Skill> skills = skillRepository.findByUserId(userId);
        
        Map<String, Integer> skillsMap = skills.stream()
                .collect(Collectors.toMap(
                        Skill::getSkillName,
                        Skill::getProficiencyLevel
                ));

        return SkillsDTO.builder()
                .userId(userId)
                .skills(skillsMap)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(Integer userId) {
        return userRepository.existsById(userId);
    }
}