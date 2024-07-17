package com.nlw.planner.participant;

import com.nlw.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public List<ParticipantBase> getAllParticipants(UUID tripId) {
        Optional<List<Participant>> participantsDB = this.participantRepository.findByTripId(tripId);
        if(participantsDB.isPresent() && !participantsDB.get().isEmpty()) {
            return participantsDB.get().stream().map(participant -> new ParticipantBase(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
        }
        return new ArrayList<ParticipantBase>();
    }

    public List<ParticipantBase> registerParticipantsToTrip(List<String> participantsToInvite, Trip trip) {
        List<Participant> participants = participantsToInvite.stream().map(name -> new Participant(name, trip)).collect(Collectors.toList());
        this.participantRepository.saveAll(participants);
        return participants.stream().map(participant -> new ParticipantBase(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {}

    public void triggerConfirmationEmailToParticipants(List<ParticipantBase> participants) {}
}

