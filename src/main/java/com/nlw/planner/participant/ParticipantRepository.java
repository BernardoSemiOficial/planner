package com.nlw.planner.participant;

import com.nlw.planner.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    Optional<List<Participant>> findByTripId(UUID id);
}
