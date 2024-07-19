package com.xclone.userservice.repository.db.dao;

import com.xclone.userservice.repository.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserId(UUID id);

    Optional<User> findUserByEmail(String email);

    Boolean existsUserByEmail(String email);

    Optional<User> findByEmailAndActivationCode(String email, String activateCode);

    Optional<User> findByEmailAndPasswordResetCode(String email, String passwordResetCode);

    List<User> findUsersByUserIdIn(Collection<UUID> userIds);
}
