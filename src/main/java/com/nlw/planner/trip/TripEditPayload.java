package com.nlw.planner.trip;

import java.util.List;
import java.util.UUID;

public record TripEditPayload(UUID id, String starts_at, String ends_at, String destination) {
}
