package com.backend.music_event.controllers;

import com.backend.music_event.model.Contact;


import com.backend.music_event.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Contact> submitMessage(@RequestBody Contact contact) {
        Contact savedContact = contactService.saveMessage(contact);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllMessages() {
        List<Contact> messages = contactService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Contact>> getUnreadMessages() {
        List<Contact> unreadMessages = contactService.getUnreadMessages();
        return ResponseEntity.ok(unreadMessages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getMessageById(@PathVariable Long id) {
        return contactService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Contact> markAsRead(@PathVariable Long id) {
        Contact updatedMessage = contactService.markAsRead(id);
        if (updatedMessage == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        contactService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
