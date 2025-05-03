package pl.edu.pjatk.s25819.smartcity.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Profile;
import pl.edu.pjatk.s25819.smartcity.entities.profiles.Role;
import pl.edu.pjatk.s25819.smartcity.services.profiles.ProfileService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
public class Bootstrap {

    private final ProfileService profilesService;

    @Bean
    public CommandLineRunner sampleData() {
        return args -> {

            Instant defaultDate = LocalDate.of(2025, 1, 2)
                    .atStartOfDay()
                    .toInstant(ZoneOffset.UTC);


            // Dodawanie roli
            profilesService.saveRole(new Role("ROLE_GUEST"));
            profilesService.saveRole(new Role("ROLE_USER"));
            profilesService.saveRole(new Role("ROLE_ADMIN"));

            // Dodawanie profili użytkowników
            profilesService.saveProfile(new Profile("Czarek", "Klasicki", "admin", "admin", "cezary.klasicki@pjwstk.edu.pl", defaultDate, new ArrayList<>()));
            profilesService.saveProfile(new Profile("Anna", "Nowak", "anowak", "pass123", "anna.nowak@example.com", defaultDate, new ArrayList<>()));
            profilesService.saveProfile(new Profile("Jan", "Kowalski", "jkowalski", "pass123", "jan.kowalski@example.com", defaultDate, new ArrayList<>()));
            profilesService.saveProfile(new Profile("Ewa", "Wiśniewska", "ewaw", "pass123", "ewa.w@example.com", defaultDate, new ArrayList<>()));
            profilesService.saveProfile(new Profile("Marek", "Zieliński", "marekz", "pass123", "marek.z@example.com", defaultDate, new ArrayList<>()));

            // Przypisywanie ról do profili
            profilesService.addRoleToProfile("admin", "ROLE_ADMIN");
            profilesService.addRoleToProfile("admin", "ROLE_USER");
            profilesService.addRoleToProfile("anowak", "ROLE_USER");
            profilesService.addRoleToProfile("jkowalski", "ROLE_USER");
            profilesService.addRoleToProfile("marekz", "ROLE_USER");
        };
    }
}
