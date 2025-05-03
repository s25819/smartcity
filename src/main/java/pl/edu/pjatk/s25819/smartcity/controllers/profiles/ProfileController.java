package pl.edu.pjatk.s25819.smartcity.controllers.profiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.s25819.smartcity.dtos.profiles.ProfileMapper;
import pl.edu.pjatk.s25819.smartcity.dtos.profiles.ProfileResponseDto;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Profile;
import pl.edu.pjatk.s25819.smartcity.services.profiles.ProfileService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/profiles/")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("")
    public ResponseEntity<List<ProfileResponseDto>> getAllProfiles() {
        var profiles = profileService.getAllProfiles();

        var profileDtos = profiles.stream()
                .map(ProfileMapper::toDto)
                .toList();

        return ResponseEntity.ok(profileDtos);
    }
}
