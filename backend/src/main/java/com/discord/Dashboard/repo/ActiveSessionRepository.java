package com.discord.Dashboard.repo;

import com.discord.Dashboard.entity.ActiveSession;
import com.discord.Dashboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveSessionRepository extends JpaRepository<ActiveSession, Long> {
    Optional<ActiveSession> findTopByOrderByIdAsc();
    void deleteAll();
    void deleteByUser(UserEntity user);

}

