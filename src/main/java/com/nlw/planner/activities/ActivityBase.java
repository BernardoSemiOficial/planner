package com.nlw.planner.activities;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityBase(UUID id, String title, Boolean isFinished, LocalDateTime occursAt) {
}
