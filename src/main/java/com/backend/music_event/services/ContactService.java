package com.backend.music_event.services;

import com.backend.music_event.model.Contact;

import com.backend.music_event.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllMessages() {
        return contactRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Contact> getUnreadMessages() {
        return contactRepository.findByReadOrderByCreatedAtDesc(false);
    }

    public Optional<Contact> getMessageById(Long id) {
        return contactRepository.findById(id);
    }

    public Contact saveMessage(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact markAsRead(Long id) {
        return contactRepository.findById(id)
                .map(message -> {
                    message.setRead(true);
                    return contactRepository.save(message);
                })
                .orElse(null);
    }

    public void deleteMessage(Long id) {
        contactRepository.deleteById(id);
    }
}
