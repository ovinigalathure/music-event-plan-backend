package com.backend.music_event.controller;

import com.backend.music_event.model.Subscription;
import com.backend.music_event.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionRequest request) {
        try {
            if (request.getUserId() == null || request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return new ResponseEntity<>(new ErrorResponse("User ID and email are required"), HttpStatus.BAD_REQUEST);
            }
            Subscription subscription = subscriptionService.subscribe(request.getUserId(), request.getEmail());
            return new ResponseEntity<>(new SubscriptionResponse("Successfully subscribed to the newsletter"),
                    HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to subscribe. Please try again later."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DTOs
    public static class SubscriptionRequest {
        private Long userId;
        private String email;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class SubscriptionResponse {
        private String message;

        public SubscriptionResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
