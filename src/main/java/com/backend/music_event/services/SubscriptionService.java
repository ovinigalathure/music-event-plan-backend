package com.backend.music_event.services;

import com.backend.music_event.model.Subscription;
import com.backend.music_event.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription subscribe(Long userId, String email) {
        if (subscriptionRepository.existsByUserIdAndEmail(userId, email)) {
            throw new IllegalArgumentException("This email is already subscribed to our newsletter");
        }
        Subscription subscription = new Subscription(userId, email);
        return subscriptionRepository.save(subscription);
    }
}