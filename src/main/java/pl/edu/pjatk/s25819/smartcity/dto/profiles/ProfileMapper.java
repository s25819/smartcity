package pl.edu.pjatk.s25819.smartcity.dto.profiles;

import pl.edu.pjatk.s25819.smartcity.entities.profiles.Profile;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Role;

import java.util.Collection;
import java.util.List;

public class ProfileMapper {

    public static ProfileResponseDto toDto(Profile profile) {
        return new ProfileResponseDto(
                profile.getUsername(),
                profile.getEmail(),
                profile.getFirstName(),
                profile.getLastName(),
                getRoles(profile.getRoles())
        );
    }

    private static List<String> getRoles(Collection<Role> roles) {
        return roles.stream()
                .map(Role::getRoleName)
                .toList();
    }

}
