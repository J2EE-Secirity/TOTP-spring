package au.com.totp.example.security.totp.exception;

import org.springframework.security.core.AuthenticationException;

public class MissingTOTPKeyAuthenticatorException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public MissingTOTPKeyAuthenticatorException(String msg) {
        super(msg);
    }
}
