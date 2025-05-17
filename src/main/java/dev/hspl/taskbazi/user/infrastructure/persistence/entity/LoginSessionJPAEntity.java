package dev.hspl.taskbazi.user.infrastructure.persistence.entity;

import dev.hspl.taskbazi.user.domain.value.LoginSessionState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

// we should have a scheduled task for deleting old finished tokens & sessions(IMPORTANT)

@Entity(name = "LoginSession")
@Table(name = "login_sessions")
@Getter
@Setter
@ToString
public class LoginSessionJPAEntity {
    @Id
    @Column(nullable = false,name = "id",updatable = false,columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false,name = "user_id",updatable = false,columnDefinition = "UUID")
    private UUID userId;

    @Column(nullable = false,name = "refresh_count")
    private int numberOfTokenRefresh;

    @Column(nullable = false,name = "state")
    @Enumerated(EnumType.STRING)
    private LoginSessionState state;

    @Column(nullable = false,name = "request_client_id",length = 50)
    private String requestClientIdentifier;

    @Column(nullable = true,name = "request_identify_details")
    private String requestIdentificationDetails;
    // maybe it is better to use jpa AttributeConverter interface for json mapping to java class (RequestIdentificationDetails)

    @Column(nullable = false,name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true,name = "state_updated_at",insertable = false)
    private LocalDateTime stateUpdatedAt;

    @Column(nullable = true,name = "version")
    @Version
    private Integer version;
}
