package za.co.recruitmentzone.configuration;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import za.co.recruitmentzone.employee.entity.EmployeeDetailsService;



@Configuration
public class WebSecurityConfig {
    Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // requestMatchers(HttpMethod.DELETE,"accounts/**").hasAnyRole("SUPER_ADMIN")

        httpSecurity.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/Vacancies","/Employee/register/verifyEmail").permitAll() // Allow /guest-home for anyone
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form.loginPage("/log-in")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                //.csrf(AbstractHttpConfigurer::disable)
                .logout(LogoutConfigurer::permitAll);

        return httpSecurity.build();
    }
 /*   @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/

    @Bean
    public UserDetailsService userDetailsService(){
        return new EmployeeDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public Storage storage() {
        return StorageOptions.getDefaultInstance().getService();
    }


}
