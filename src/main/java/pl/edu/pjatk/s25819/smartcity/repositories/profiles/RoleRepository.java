package pl.edu.pjatk.s25819.smartcity.repositories.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
