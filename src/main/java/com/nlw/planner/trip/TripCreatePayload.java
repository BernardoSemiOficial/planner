package com.nlw.planner.trip;

import java.util.List;

public record TripCreatePayload(String starts_at, String ends_at, String destination, List<String> emails_to_invite, String owner_name, String owner_email) {
}
