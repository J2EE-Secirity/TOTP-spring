package au.com.totp.example.security.totp.authdetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class TOTPWebAuthenticationDetailsSource extends WebAuthenticationDetailsSource {

    @Override
    public TOTPWebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new TOTPWebAuthenticationDetails(request);
    }
}
