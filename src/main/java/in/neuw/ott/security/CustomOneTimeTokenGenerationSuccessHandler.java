package in.neuw.ott.security;

import in.neuw.ott.db.repositories.UserJPARepository;
import in.neuw.ott.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;
import org.springframework.web.servlet.view.RedirectView;

import static in.neuw.ott.utils.CommonUtils.maskEmail;
import static in.neuw.ott.utils.Constants.USER_EMAIL_NOT_EXISTS;
import static in.neuw.ott.utils.Constants.USER_NOT_RESOLVED;

@Slf4j
@Component
public class CustomOneTimeTokenGenerationSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    private final UserJPARepository userRepository;

    @Value("${login.ott.url}")
    private String ottUrl;

    private final NotificationService notificationService;

    public static final String REDIRECT_URL = "/sent";

    private final FlashMapManager flashMapManager = new SessionFlashMapManager();

    public CustomOneTimeTokenGenerationSuccessHandler(NotificationService notificationService,
                                                      UserJPARepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @Override
    @SneakyThrows
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) {
        // send notification if user actually existed.
        log.info("Received ott token : {}", maskEmail(oneTimeToken.getUsername()));
        if (!USER_NOT_RESOLVED.equals(oneTimeToken.getUsername())) {
            var username = oneTimeToken.getUsername();
            if (!username.equals(USER_NOT_RESOLVED) && !username.equals(USER_EMAIL_NOT_EXISTS)) {
                notificationService
                        .sendMagicLinkNotification(oneTimeToken.getUsername(), ottUrl + oneTimeToken.getTokenValue());
                log.info("ott notification sent for user {}", maskEmail(username));
            } else {
                log.error("User {} has no email", username);
            }
        }
        RedirectView redirectView = new RedirectView(REDIRECT_URL);
        redirectView.setExposeModelAttributes(false);
        FlashMap flashMap = new FlashMap();
        flashMap.put("from", "oneTimeTokenGenerationFlow");
        flashMapManager.saveOutputFlashMap(flashMap, request, response);
        redirectView.render(flashMap, request, response);
        //dispatcher.forward(request, response);
    }
}
