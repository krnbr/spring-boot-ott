package in.neuw.ott.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class Constants {

    public static final String USER_NOT_RESOLVED = "user_not_resolved";
    public static final String USER_EMAIL_NOT_EXISTS = "user_email_not_exists";
    public static final String NON_SENSE_TOKEN = "non_sense_token";

    public static final String USER_ROLE = "USER";
    public static final String NON_EMAIL_USER_ROLE = "NON_EMAIL_USER";

    public static final SimpleGrantedAuthority USER_AUTH = new SimpleGrantedAuthority(USER_ROLE);
    public static final SimpleGrantedAuthority NON_EMAIL_AUTH = new SimpleGrantedAuthority(NON_EMAIL_USER_ROLE);

}
