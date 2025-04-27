// TeamMember.java
package com.backend.music_event.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "team_members")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
    private String imagePath;

    @ElementCollection
    @CollectionTable(name = "team_member_skills", joinColumns = @JoinColumn(name = "team_member_id"))
    @Column(name = "skill")
    private List<String> skills;

    // Constructors
    public TeamMember() {}

    public TeamMember(String name, String position, String imagePath, List<String> skills) {
        this.name = name;
        this.position = position;
        this.imagePath = imagePath;
        this.skills = skills;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
}
