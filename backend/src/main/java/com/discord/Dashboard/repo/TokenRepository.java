package com.discord.Dashboard.repo;

import com.discord.Dashboard.entity.TokenEntity;
import com.discord.Dashboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    List<TokenEntity> findAllByUserAndRevokedFalse(UserEntity user);

    List<TokenEntity> findAllByRevokedFalse();

    Optional<TokenEntity> findByToken(String token);
}

