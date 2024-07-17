package com.nlw.planner.participant;

import java.util.UUID;

public record ParticipantBase(UUID id, String name, String email, Boolean isConfirmed) {
}
