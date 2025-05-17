package dev.hspl.taskbazi.user.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

// optimistic locking in database needed for preventing double refresh
// we should have a scheduled task for deleting old finished tokens & sessions(IMPORTANT)

@Entity(name = "RefreshToken")
@Table(name = "refresh_tokens")
@Getter
@Setter
@ToString
public class RefreshTokenJPAEntity {
    @Id
    @Column(nullable = false,name = "id",updatable = false,columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false,name = "token",updatable = false)
    private String hashedActualToken;

    @Column(nullable = false,name = "lifetime_hours")
    private short lifetimeHours;

    @Column(nullable = false,name = "refreshed")
    private boolean refreshed;

    @Column(nullable = false,name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true,name = "refreshed_at",insertable = false)
    private LocalDateTime refreshedAt;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "session_id",referencedColumnName = "id")
    @ToString.Exclude
    private LoginSessionJPAEntity loginSession;

    @Column(nullable = true,name = "version")
    @Version
    private Short version; // optimistic locking is important for this entity
}
