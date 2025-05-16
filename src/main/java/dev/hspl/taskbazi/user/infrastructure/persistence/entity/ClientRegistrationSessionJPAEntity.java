package dev.hspl.taskbazi.user.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

// we should have a scheduled task to remove old garbage registration sessions

@Entity(name = "ClientRegistrationSession")
@Table(name = "client_registration_sessions")
@Getter
@Setter
@ToString
public class ClientRegistrationSessionJPAEntity {
    @Id
    @Column(nullable = false,name = "id",updatable = false,columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false,name = "email",updatable = false,length = 60)
    private String emailAddress;

    @Column(nullable = false,name = "full_name",updatable = false,length = 40)
    private String clientFullName;

    @Column(nullable = false,name = "username",updatable = false,length = 30)
    private String clientUsername;

    @Column(nullable = false,name = "password",updatable = false)
    private String clientHashedPassword;

    @Column(nullable = false,name = "verification_code",updatable = false)
    private String hashedVerificationCode;

    @Column(nullable = false,name = "request_client_id",updatable = false,length = 50)
    private String requestClientIdentifier;

    @Column(nullable = false,name = "attempts")
    private short verificationAttempts;

    @Column(nullable = false,name = "blocked")
    private boolean blocked;

    @Column(nullable = false,name = "verified")
    private boolean verified;

    @Column(nullable = false,name = "registered")
    private boolean registered;

    @Column(nullable = false,name = "expired")
    private boolean expired;

    @Column(nullable = false,name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true,name = "closed_at",insertable = false)
    private LocalDateTime closedAt;

    @Column(nullable = true,name = "version")
    @Version
    private Short version;
}
