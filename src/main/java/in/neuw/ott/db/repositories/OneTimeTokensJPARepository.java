package in.neuw.ott.db.repositories;

import in.neuw.ott.db.entities.OneTimeTokensEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface OneTimeTokensJPARepository extends CrudRepository<OneTimeTokensEntity, String> {

    @Transactional
    List<OneTimeTokensEntity> deleteAllByExpiresAtBefore(Instant expiresAtBefore);

}
