package com.nlw.planner.activities;

import com.nlw.planner.trip.Trip;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record ActivityCreatePaylod(String title, String occursAt, Trip trip) {
}
