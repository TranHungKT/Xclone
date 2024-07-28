package com.xclone.userservice.repository.db.entity;

import com.xclone.userservice.repository.db.helper.EntityHelper;
import com.xclone.userservice.requestDto.RegistrationRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = EntityHelper.USER_TABLE)
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(unique = true, name = "email")
    @NotNull
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String fullName;
    private String username;
    private String location;
    private String about;
    private String website;
    private boolean confirmed;
    private String activationCode;
    private String passwordResetCode;
    private String role;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = Tweet_.USER, cascade = CascadeType.ALL)
    private List<Tweet> tweets = List.of();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = EntityHelper.TWEET_LIKES_TABLE,
            joinColumns = @JoinColumn(name = User_.USER_ID),
            inverseJoinColumns = @JoinColumn(name = Tweet_.TWEET_ID)
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Tweet> likedTweets;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = EntityHelper.RETWEETS_TABLE,
            joinColumns = @JoinColumn(name = User_.USER_ID),
            inverseJoinColumns = @JoinColumn(name = Tweet_.TWEET_ID)
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Tweet> retweets;

    @ManyToMany(mappedBy = User_.FOLLOWERS, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> following;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinTable(name = EntityHelper.USER_SUBSCRIPTIONS,
            joinColumns = @JoinColumn(name = User_.USER_ID),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private Set<User> followers;

    public static User from(@NonNull RegistrationRequest request, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdBy(request.getFullName())
                .fullName(request.getFullName())
                .role("USER")
                .activationCode(UUID.randomUUID().toString())
                .confirmed(false)
                .build();
    }
}
