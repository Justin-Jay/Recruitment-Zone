package za.co.recruitmentzone.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import java.io.IOException;

public class HxRefreshHeaderAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationEntryPoint forbiddenEntryPoint;

    public HxRefreshHeaderAuthenticationEntryPoint() {
        this.forbiddenEntryPoint = new Http403ForbiddenEntryPoint();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("HX-Refresh", "true");
        forbiddenEntryPoint.commence(request, response, authException);
    }
}
