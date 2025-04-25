package com.backend.music_event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.backend.music_event.model")
public class MusicEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicEventApplication.class, args);
	}

}
