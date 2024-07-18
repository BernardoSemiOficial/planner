package com.nlw.planner.activity;

import com.nlw.planner.trip.Trip;

public record ActivityCreatePaylod(String title, String occursAt, Trip trip) {
}
