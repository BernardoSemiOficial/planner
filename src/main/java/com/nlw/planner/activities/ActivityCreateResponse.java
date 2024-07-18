package com.nlw.planner.activities;

import com.nlw.planner.trip.Trip;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityCreateResponse(UUID id, String title, Boolean isFinished, LocalDateTime occursAt) {
}
