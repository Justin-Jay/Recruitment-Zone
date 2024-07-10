package za.co.recruitmentzone.configuration;


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
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import za.co.recruitmentzone.employee.entity.EmployeeDetailsService;


@Configuration
public class WebSecurityConfig {
    Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {


        String[] myPaths = {"/css/**","/js/**","webjars/**","blog-images/**","vacancy-images/**","/ckeditor5/**","/home", "/Blog/blogs",
                "/Blog/view-home-blog","/aboutus","/Communication/send-message","/Communication/contact-us",
                "/Application/apply-now","/Application/save-application","/Employee/register/verifyEmail",
                "/Document/searchDocuments","/Document/searchFileContents","/Vacancy/view-home-vacancy"};

        String[] promPaths = {"/actuator/prometheus/"};

        //var entryPoint = new HxRefreshHeaderAuthenticationEntryPoint();
        //var requestMatcher = new RequestHeaderRequestMatcher("HX-Request");

        httpSecurity.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(myPaths).permitAll()
                                .requestMatchers(promPaths).hasAuthority("PROMETHEUS")
                                .anyRequest().authenticated()
                )
                .httpBasic(customizer -> { /* TODO CONFIG */ })
                .formLogin(form ->
                        form.loginPage("/log-in")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                .logout(LogoutConfigurer::permitAll);
                //.exceptionHandling(exception ->
                       // exception.defaultAuthenticationEntryPointFor(entryPoint,
                        //        requestMatcher));

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

    @Bean
    public HttpFirewall allowSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }




}

