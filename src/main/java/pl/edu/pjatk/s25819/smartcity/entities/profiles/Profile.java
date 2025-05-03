package pl.edu.pjatk.s25819.smartcity.entities.profiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import pl.edu.pjatk.s25819.smartcity.entities.BaseEntity;

import java.time.Instant;
import java.util.Collection;

@Entity(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Profile extends BaseEntity<Long> {

    @ToString.Include
    @Column(nullable = false)
    private String firstName;

    @ToString.Include
    @Column(nullable = false)
    private String lastName;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name="created_at", updatable = false)
    @CreatedDate
    private Instant createdAt;

    @ManyToMany
    @ToString.Exclude
    private Collection<Role> roles;
}
