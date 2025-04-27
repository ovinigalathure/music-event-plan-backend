// TeamMemberService.java
package com.backend.music_event.services;

import com.backend.music_event.model.TeamMember;
import com.backend.music_event.repositories.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public List<TeamMember> getAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public Optional<TeamMember> getTeamMemberById(Long id) {
        return teamMemberRepository.findById(id);
    }

    public TeamMember createTeamMember(TeamMember teamMember) {
        return teamMemberRepository.save(teamMember);
    }

    public Optional<TeamMember> updateTeamMember(Long id, TeamMember updatedMember) {
        return teamMemberRepository.findById(id).map(member -> {
            member.setName(updatedMember.getName());
            member.setPosition(updatedMember.getPosition());
            member.setImagePath(updatedMember.getImagePath());
            member.setSkills(updatedMember.getSkills());
            return teamMemberRepository.save(member);
        });
    }

    public void deleteTeamMember(Long id) {
        teamMemberRepository.deleteById(id);
    }
}
