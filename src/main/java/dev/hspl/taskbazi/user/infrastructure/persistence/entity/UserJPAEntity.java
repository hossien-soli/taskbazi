package dev.hspl.taskbazi.user.infrastructure.persistence.entity;

import dev.hspl.taskbazi.common.domain.value.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

// jpa-entity/model for all type/role of users in a single table
// actually domain allow us to store different type/role of users inside separate tables

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
@ToString
public class UserJPAEntity {
    @Id
    @Column(nullable = false,name = "id",updatable = false,columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false,name = "full_name",length = 40)
    private String fullName;

    @Column(nullable = false,name = "email",length = 60,unique = true)
    private String emailAddress;

    @Column(nullable = false,name = "username",length = 30,unique = true)
    private String username;

    @Column(nullable = false,name = "password")
    private String hashedPassword;

    @Column(nullable = false,name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false,name = "banned",columnDefinition = "boolean")
    private boolean banned;

    @Column(nullable = false,name = "registered_at",updatable = false)
    private LocalDateTime registeredAt;

    @Column(nullable = true,name = "version")
    @Version
    private Integer version;
    // The client must receive and return the version attribute for pre-update validation. in the Domain as a Domain RULE
    // Using ETag & If-Match headers (412 Precondition Failed)
}
