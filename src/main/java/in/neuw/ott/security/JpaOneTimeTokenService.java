package in.neuw.ott.security;

import in.neuw.ott.db.entities.OneTimeTokensEntity;
import in.neuw.ott.db.repositories.OneTimeTokensJPARepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.authentication.ott.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static in.neuw.ott.utils.CommonUtils.UUIDv7;
import static in.neuw.ott.utils.CommonUtils.maskEmail;
import static in.neuw.ott.utils.Constants.*;

/**
 * A JPA implementation of an {@link OneTimeTokenService} that uses a
 * {@link org.springframework.data.repository.CrudRepository} for {@link OneTimeToken} persistence.
 */
@Slf4j
public class JpaOneTimeTokenService implements OneTimeTokenService, DisposableBean, InitializingBean {

	private final OneTimeTokensJPARepository repository;
    private final CustomUserDetailsService userDetailsService;

    private Clock clock = Clock.systemUTC();

	private ThreadPoolTaskScheduler taskScheduler;

	private static final String DEFAULT_CLEANUP_CRON = "@hourly";

	private Duration expiry = Duration.ofMinutes(5);

	public JpaOneTimeTokenService(OneTimeTokensJPARepository oneTimeTokenRepository,
								  CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        Assert.notNull(oneTimeTokenRepository, "oneTimeTokenRepository cannot be null");
		this.repository = oneTimeTokenRepository;
		this.taskScheduler = createTaskScheduler(DEFAULT_CLEANUP_CRON);
	}

	public void setExpiryTime(Duration expiryTime) {
		this.expiry = expiryTime;
	}

	public void setCleanupCron(String cleanupCron) {
		this.taskScheduler = createTaskScheduler(cleanupCron);
	}

	@Override
	public OneTimeToken generate(GenerateOneTimeTokenRequest request) {
		log.info("Generating OneTimeToken");
		Assert.notNull(request, "generateOneTimeTokenRequest cannot be null");
		DefaultOneTimeToken oneTimeToken;
		try {
			var user = userDetailsService.loadUserByUsernameForOtt(request.getUsername());
			if (!user.getAuthorities().contains(NON_EMAIL_AUTH)) { // normal user that has email associated.
				oneTimeToken = new DefaultOneTimeToken(UUIDv7(), user.getUsername(), this.clock.instant().plus(expiry));
				insertOneTimeToken(oneTimeToken);
			} else { // a user that exists but does not have an email, well this will be deprecated in near future.
				oneTimeToken = new DefaultOneTimeToken(NON_SENSE_TOKEN, USER_EMAIL_NOT_EXISTS, this.clock.instant().plus(expiry));
			}
			return oneTimeToken;
		} catch (UsernameNotFoundException e) {
			log.error("Username not found - {}", maskEmail(request.getUsername()));
            oneTimeToken = new DefaultOneTimeToken(NON_SENSE_TOKEN, USER_NOT_RESOLVED, this.clock.instant());
		} catch (Exception e) {
			log.error("Exception {} while fetching user not found - {}", e.getCause(), maskEmail(request.getUsername()));
			oneTimeToken = new DefaultOneTimeToken(NON_SENSE_TOKEN, USER_NOT_RESOLVED, this.clock.instant());
		}
		return oneTimeToken;
	}

	private void insertOneTimeToken(OneTimeToken oneTimeToken) {
		var oneTimeTokensEntity = new OneTimeTokensEntity();
		oneTimeTokensEntity.setUsername(oneTimeToken.getUsername());
		oneTimeTokensEntity.setExpiresAt(oneTimeToken.getExpiresAt());
		oneTimeTokensEntity.setId(oneTimeToken.getTokenValue());
		repository.save(oneTimeTokensEntity);
	}

	@Override
	public OneTimeToken consume(OneTimeTokenAuthenticationToken authenticationToken) {
		Assert.notNull(authenticationToken, "authenticationToken cannot be null");

		Optional<OneTimeTokensEntity> optionalOneTimeTokensEntity = selectOneTimeToken(authenticationToken);
		if (optionalOneTimeTokensEntity.isEmpty()) {
			return null;
		}
		var oneTimeTokensEntity = optionalOneTimeTokensEntity.get();
		var token = new DefaultOneTimeToken(oneTimeTokensEntity.getId(), oneTimeTokensEntity.getUsername(), oneTimeTokensEntity.getExpiresAt());
		deleteOneTimeToken(token);
		if (isExpired(token)) {
			return null;
		}
		return token;
	}

	private boolean isExpired(OneTimeToken ott) {
		return this.clock.instant().isAfter(ott.getExpiresAt());
	}

	private Optional<OneTimeTokensEntity> selectOneTimeToken(OneTimeTokenAuthenticationToken authenticationToken) {
		return repository.findById(authenticationToken.getTokenValue());
	}

	private void deleteOneTimeToken(OneTimeToken oneTimeToken) {
		repository.deleteById(oneTimeToken.getTokenValue());
	}

	@Transactional
    protected ThreadPoolTaskScheduler createTaskScheduler(String cleanupCron) {
		if (cleanupCron == null) {
			return null;
		}
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setThreadNamePrefix("spring-one-time-tokens-");
		taskScheduler.initialize();
		taskScheduler.schedule(this::cleanupExpiredTokens, new CronTrigger(cleanupCron));
		return taskScheduler;
	}

	@Transactional
	public void cleanupExpiredTokens() {
		int deletedCount = repository.deleteAllByExpiresAtBefore(Instant.now()).size();
		if (log.isDebugEnabled()) {
			log.debug("Cleaned up " + deletedCount + " expired tokens");
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.taskScheduler.afterPropertiesSet();
	}

	@Override
	public void destroy() throws Exception {
		if (this.taskScheduler != null) {
			this.taskScheduler.shutdown();
		}
	}

	public void setClock(Clock clock) {
		Assert.notNull(clock, "clock cannot be null");
		this.clock = clock;
	}

}