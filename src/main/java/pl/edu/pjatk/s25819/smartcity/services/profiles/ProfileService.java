package pl.edu.pjatk.s25819.smartcity.services.profiles;

import pl.edu.pjatk.s25819.smartcity.entities.profiles.Profile;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Role;

import java.util.List;
import java.util.Map;

public interface ProfileService {

    Profile getProfileById(Long id);

    Boolean existsByUsername(String username);

    Profile getProfileByUsername(String username);

    List<Profile> getAllProfiles();

    Profile saveProfile(Profile profile);

    Role saveRole(Role role);

    void addRoleToProfile(String username, String role);

    Boolean isPasswordValid(String username, String password);

    Map<String, String> authenticate(String username, String password);
}
