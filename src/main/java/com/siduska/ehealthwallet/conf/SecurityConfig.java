package com.siduska.ehealthwallet.conf;

import com.siduska.ehealthwallet.entitiy.User;
import com.siduska.ehealthwallet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    CommandLineRunner init(PasswordEncoder encoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setUsername("admin");
                user.setEmail("mojemail@localhost");
                user.setPassword(encoder.encode("password"));
                userRepository.save(user);

                System.out.println("Default admin user created: username=admin, password=password");
            }
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/ws/**",
                                "/topic/**",
                                "/app/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/h2-console/**")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //fixes H2 console display in browser
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/ws/**", "/topic/**", "/app/**").permitAll()
                        .requestMatchers("/actuator/**", "/health/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
