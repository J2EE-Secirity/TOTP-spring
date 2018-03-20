package au.com.totp.example.security.config;

import au.com.totp.example.security.totp.authdetails.TOTPWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**");
        web.ignoring().antMatchers("/resources/**");
    }

    @Autowired
    private TOTPConfigurator totpConfigurator;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(AUTH_WHITELIST).permitAll() // whitelist Swagger UI resources
                    .antMatchers("/login", "/rest/**", "/userAdmin.html", "/webjars/**", "/userAdmin.js")
                        .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/welcome.html")
                    .failureUrl("/login?error=true")
                    .permitAll()
                .and()
                .logout()
                    .permitAll()
                .and()
                .csrf()
                    .disable();
        totpConfigurator.configureAuthenticationDetailsSource(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        totpConfigurator.configureAuthenticationProvider(auth);
    }
}
