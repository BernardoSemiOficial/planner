package com.nlw.planner.trip;

import java.util.List;

public record TripInviteParticipantsPayload(List<String> emails_to_invite) {
}
