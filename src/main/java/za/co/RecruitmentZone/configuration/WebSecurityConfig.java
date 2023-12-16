package za.co.RecruitmentZone.configuration;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {
    Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // requestMatchers(HttpMethod.DELETE,"accounts/**").hasAnyRole("SUPER_ADMIN")

        httpSecurity.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").permitAll() // Allow /guest-home for anyone
                                .anyRequest().authenticated() // Require authentication for other requests
                )
                .formLogin(form ->
                        form.loginPage("/log-in")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                .logout(LogoutConfigurer::permitAll);
        return httpSecurity.build();
    }
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public Storage storage() {
        return StorageOptions.getDefaultInstance().getService();
    }



}
