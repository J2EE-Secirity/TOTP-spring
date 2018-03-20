package au.com.totp.example.security.totp.authdetails;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TOTPWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Integer totpKey; // Это мое кастомное поле (для двухфакторной аудитенфикации...)

    /**
     * Записывает удаленный адрес и также устанавливает идентификатор сеанса, если сеанс уже существует (он не будет создан).
     *
     * @param request что запрос аутентификации был получен из
     */
    public TOTPWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String totpKeyString = request.getParameter("TOTPKey");
        if (StringUtils.hasText(totpKeyString)) {
            try {
                this.totpKey = Integer.valueOf(totpKeyString);
            } catch (NumberFormatException e) {
                this.totpKey = null;
            }
        }
    }

    public Integer getTotpKey() {
        return this.totpKey;
    }
}
