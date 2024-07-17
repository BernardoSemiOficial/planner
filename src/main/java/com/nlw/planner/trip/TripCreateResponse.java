package com.nlw.planner.trip;

import com.nlw.planner.participant.Participant;
import com.nlw.planner.participant.ParticipantBase;

import java.util.List;
import java.util.UUID;

public record TripCreateResponse(UUID id, List<ParticipantBase> participants) {
}
