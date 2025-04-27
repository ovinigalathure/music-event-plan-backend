package com.backend.music_event.repository;

import com.backend.music_event.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByEmail(String email);
    boolean existsByUserIdAndEmail(Long userId, String email);
}
