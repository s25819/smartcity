package pl.edu.pjatk.s25819.smartcity.entities.profiles;

import jakarta.persistence.Entity;
import lombok.*;
import pl.edu.pjatk.s25819.smartcity.entities.BaseEntity;

@Entity(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role extends BaseEntity<Long> {

    @ToString.Include
    @EqualsAndHashCode.Include
    private String roleName;
}

