package pl.edu.pjatk.s25819.smartcity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaAuditing
public class SmartcityDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartcityDashboardApplication.class, args);
	}

}
