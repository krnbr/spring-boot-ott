package in.neuw.ott.config;

import in.neuw.ott.db.repositories.OneTimeTokensJPARepository;
import in.neuw.ott.db.repositories.UserJPARepository;
import in.neuw.ott.security.CustomOneTimeTokenGenerationSuccessHandler;
import in.neuw.ott.security.CustomUserDetailsService;
import in.neuw.ott.security.JpaOneTimeTokenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomUserDetailsService userDetailsService(UserJPARepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    OneTimeTokenService jpaOneTimeTokenService(OneTimeTokensJPARepository repository,
                                               CustomUserDetailsService userDetailsService) {
        return new JpaOneTimeTokenService(repository, userDetailsService);
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   OneTimeTokenService jpaOneTimeTokenService,
                                                   CustomOneTimeTokenGenerationSuccessHandler oneTimeTokenGenerationSuccessHandler) {
        http.authorizeHttpRequests(ar -> {
            ar.requestMatchers("/login/**", "/magic/link/**", "/sent", "/logout", "/css/**", "/js/**", "/imgs/**", "/apis/**")
                    .permitAll();
            ar.anyRequest().authenticated();
        });
        http.formLogin(f -> f.loginPage("/login"));
        http.logout(Customizer.withDefaults());
        http.csrf(Customizer.withDefaults());
        // the defaults one does not work without a custom success handler
        //http.oneTimeTokenLogin(Customizer.withDefaults());
        /*http.oneTimeTokenLogin(o -> {
            o.tokenGenerationSuccessHandler((request, response, oneTimeToken) -> {
                System.out.println("token generation success -> go here to login - /login/ott?token=" + oneTimeToken.getTokenValue());
                response.sendRedirect("/");
            });
        });*/
        http.oneTimeTokenLogin(o -> {
            o.tokenService(jpaOneTimeTokenService);
            o.defaultSubmitPageUrl("/login/ott");
            o.tokenGeneratingUrl("/magic/link/generate");
            o.loginProcessingUrl("/magic/link/submit");
            o.showDefaultSubmitPage(false);
            o.tokenGenerationSuccessHandler(oneTimeTokenGenerationSuccessHandler);
            /*o.authenticationSuccessHandler((request, response, authentication) -> {
                response.sendRedirect("/");
            });*/
            o.authenticationFailureHandler((request, response, exception) -> {
                log.error("Authentication failure", exception);
                response.sendRedirect("/login?error=ott");
            });
        });
        return http.build();
    }

}
