package za.co.recruitmentzone.configuration;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.csrf.*;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import za.co.recruitmentzone.employee.entity.EmployeeDetailsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


@Configuration
public class WebSecurityConfig {
    Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {


        String[] myPaths = {"/css/**","/js/**","webjars/**","/home", "/Blog/blogs","/Blog/view-home-blog", "/aboutus","/Communication/send-message",
                "/Communication/contact-us","/Application/apply-now","/Application/save-application",
                "/Employee/register/verifyEmail", "/Document/searchDocuments","/Document/searchFileContents","/Vacancy/view-home-vacancy","/actuator/prometheus"};


        httpSecurity.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(myPaths).permitAll()
                                //.requestMatchers("/Client/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form.loginPage("/log-in")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                /* .csrf(csrf -> csrf
                         .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                         .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
                 )
                 .securityContext((securityContext) -> securityContext
                         .securityContextRepository(new DelegatingSecurityContextRepository(
                                         new RequestAttributeSecurityContextRepository(),
                                         new HttpSessionSecurityContextRepository()
                                 )
                         ).requireExplicitSave(true)
                 )*/
                //.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .logout(LogoutConfigurer::permitAll);

        return httpSecurity.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
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


}

