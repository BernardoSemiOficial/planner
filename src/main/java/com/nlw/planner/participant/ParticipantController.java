package com.nlw.planner.participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

    @PostMapping("/{participantId}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID participantId, @RequestBody ParticipantConfirmPayload payload) {
        Optional<Participant> participantDB = this.participantRepository.findById(participantId);
        if(participantDB.isPresent()) {
            Participant participant = participantDB.get();
            participant.setName(payload.name());
            participant.setIsConfirmed(true);
            System.out.println(participant);
            this.participantRepository.save(participant);
            return ResponseEntity.ok(participant);
        }
        return ResponseEntity.notFound().build();
    }
}
