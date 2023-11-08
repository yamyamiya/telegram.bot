package io.yamyamiya.telegram.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x-> x
                        .requestMatchers(HttpMethod.GET, "/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/city").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/id/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/count").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/city").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/user/delete/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/user/deletename/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/city/delete/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/city/deletename/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/city/id/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/city/count").permitAll()
                        .requestMatchers(HttpMethod.GET, "/city/usercount").permitAll()
                        .requestMatchers(HttpMethod.GET, "/city/citycount/*").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/user/id/*").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/user/count").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/user").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/city").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "user/delete/*").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "user/deletename/*").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "city/delete/*").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "city/deletename/*").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/city/id/*").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/city/count").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/city/usercount/*").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/city/citycount/*").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }
}
