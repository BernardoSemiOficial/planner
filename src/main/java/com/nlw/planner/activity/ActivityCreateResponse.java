package com.nlw.planner.activity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityCreateResponse(UUID id, String title, Boolean isFinished, LocalDateTime occursAt) {
}
