package rs.aleksa.simpletoolmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rs.aleksa.simpletoolmanager.security.FirebaseAuthenticationFilter;
import rs.aleksa.simpletoolmanager.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @ConditionalOnBean(AuthService.class)
    public FirebaseAuthenticationFilter firebaseAuthenticationFilter(AuthService authService) {
        return new FirebaseAuthenticationFilter(authService);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            @Autowired(required = false) @Nullable FirebaseAuthenticationFilter firebaseFilter
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        if (firebaseFilter != null) {
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/actuator/**").permitAll()
                    .anyRequest().authenticated()
            );
            http.addFilterBefore(firebaseFilter, UsernamePasswordAuthenticationFilter.class);
            http.exceptionHandling(ex ->
                    ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            );
        } else {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        }

        return http.build();
    }
}
