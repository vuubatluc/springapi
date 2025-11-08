package com.vu.springapi.config;

import com.vu.springapi.enums.Role;
import com.vu.springapi.model.User;
import com.vu.springapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    protected ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .name("Administrator")
                        .email("admin@example.com")
                        .password(passwordEncoder.encode("admin"))
                        //.roles(roles)
                        .dob(LocalDate.of(1990, 1, 1))
                        .build();
                userRepository.save(user);
                log.warn("admin has been created with default password: admin, please change it!");
            }
        };
    }

}
