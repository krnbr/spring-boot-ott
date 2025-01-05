package in.neuw.ott.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_username", columnList = "username"),
        @Index(name = "uni_idx_email", columnList = "email", unique = true)
})
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements Persistable<String> {

    @Id
    private String id;

    @Column(nullable = false, unique = true, length = 64)
    private String username;
    private String password;

    private String firstName;
    private String middleName;
    private String lastName;

    private String email;
    private String phone;

    @Column(name = "email_verified", columnDefinition = "boolean default false")
    private boolean emailVerified;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;

    @Column(name = "enabled", columnDefinition = "boolean default false")
    private boolean enabled;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;
    private Instant lastLogin;

    @Override
    public boolean isNew() {
        return getCreated() == null;
    }
}
