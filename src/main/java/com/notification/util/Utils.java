package com.notification.util;

import com.notification.dto.EmailRequest;
import com.notification.dto.EmailResponse;
import com.notification.dto.ResponseStatus;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * Utility class.
 *
 * @author Roman Batygin
 */
@UtilityClass
public class Utils {

    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Creates email response.
     *
     * @param requestId - request id
     * @param status    - response status
     * @return email response
     */
    public static EmailResponse buildResponse(String requestId, ResponseStatus status) {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setRequestId(requestId);
        emailResponse.setStatus(status);
        return emailResponse;
    }

    /**
     * Validates email request.
     *
     * @param emailRequest - email request
     * @return {@code true} if email request is valid
     */
    public static boolean validateEmailRequest(EmailRequest emailRequest) {
        return emailRequest != null && isValidEmail(emailRequest.getSender()) && isValidEmail(emailRequest.getReceiver());
    }

    /**
     * Validates email.
     *
     * @param email - email string
     * @return {@code true} if email is valid
     */
    public static boolean isValidEmail(String email) {
        return !StringUtils.isEmpty(email) && email.matches(EMAIL_REGEX);
    }
}
