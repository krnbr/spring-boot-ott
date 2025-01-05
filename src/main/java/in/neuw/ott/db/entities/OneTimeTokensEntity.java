package in.neuw.ott.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ott")
@EntityListeners(AuditingEntityListener.class)
public class OneTimeTokensEntity implements Persistable<String> {

    @Id
    @Column(name = "token_value")
    private String id;

    private String username;

    @CreatedDate
    private Instant created;

    private Instant expiresAt;

    @Override
    public boolean isNew() {
        return getCreated() == null;
    }
}
