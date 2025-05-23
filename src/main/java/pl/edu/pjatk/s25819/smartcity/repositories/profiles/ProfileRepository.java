package pl.edu.pjatk.s25819.smartcity.repositories.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUsername(String username);
    Boolean existsByUsername(String username);
}
