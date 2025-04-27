package com.backend.music_event.repositories;

import com.backend.music_event.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // No extra methods needed for basic CRUD
}
