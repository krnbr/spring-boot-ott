package in.neuw.ott.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Map;

import static in.neuw.ott.utils.CommonUtils.maskEmail;

@Controller
public class ViewsController {

    @GetMapping("/")
    public String landing(SecurityContext securityContext, Model model, HttpServletRequest request) {
        final String username = securityContext.getAuthentication().getName();
        setCsrfToModelAttributes(request, model);
        model.addAttribute("username", maskEmail(username));
        return "pages/index";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        setCsrfToModelAttributes(request, model);
        model.addAttribute("contextPath", request.getContextPath());
        return "pages/login";
    }

    @GetMapping("/login/ott")
    public String ott(Model model, HttpServletRequest request, @RequestParam String token) {
        setCsrfToModelAttributes(request, model);
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("token", token);
        return "pages/ott-submission";
    }

    @GetMapping("/sent")
    public String sent(HttpServletRequest request) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        // takes user to default page if user tries to refresh the /sent url
        if (!CollectionUtils.isEmpty(inputFlashMap)
                && inputFlashMap.get("from") instanceof String
                && inputFlashMap.get("from") == "oneTimeTokenGenerationFlow") {
            return "pages/mail-sent";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(Model model, SecurityContext securityContext, HttpServletRequest request) {
        setCsrfToModelAttributes(request, model);
        model.addAttribute("username", maskEmail(securityContext.getAuthentication().getName()));
        return "pages/logout";
    }

    private void setCsrfToModelAttributes(HttpServletRequest request, Model model) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        var csrfHeader = renderCsrfHeader(csrfToken);
        model.addAttribute("csrfHeader", csrfHeader);
        model.addAttribute("csrfToken", csrfToken);
    }

    private String renderCsrfHeader(CsrfToken csrfToken) {
        return CSRF_HEADERS
                .replace("headerName", csrfToken.getHeaderName())
                .replace("headerValue", csrfToken.getToken());
    }

    private static final String CSRF_HEADERS = """
			{"headerName" : "headerValue"}""";

}
