package za.co.RecruitmentZone.configuration;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

import javax.sql.DataSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class WebSecurityConfig {
    Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Allow access to /guest-home without authentication
        httpSecurity.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(antMatcher("/")).permitAll() // Allow /guest-home for anyone
                                .anyRequest().authenticated() // Require authentication for other requests
                )
                .formLogin(form ->
                        form.loginPage("/log-in")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll());
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
    // /RecruitmentZone/web
  /*  @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/RecruitmentZone/web/index").permitAll();
                   // auth.requestMatchers("/user/**").hasAnyRole("ADMIN","USER");
                    auth.requestMatchers("/RecruitmentZone/api/**").hasRole("ADMIN");
                    //auth.requestMatchers("/RecruitmentZone/api/auth/**").permitAll();
                    auth.anyRequest().authenticated();
                });
        httpSecurity
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        httpSecurity
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }
*/

/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/RecruitmentZone/**").permitAll();
                    //auth.requestMatchers("/RecruitmentZone/api/auth/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }*/

/*


    /admin
        /CreateUser
        /UpdateUserDetails
        /password reset ?

    /vacancies
            /Create Vacancy
            /View Vacancies
                /Update Vacancy Status
                /Update vacancy Details

    /candidate
        /home
         : View Active Vacancies
         : Submit Application
*/


}
